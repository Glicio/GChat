package utils;

import net.luckperms.api.model.user.User;

public class FormattedMsg {
    // Provavelmente desnecessário: checa se o usuário tem a permissão para enviar
    // texto colorido
    // e depois formata o texto de acordo, depois checa se tem direito de usar texto
    // com modificadores e faz o mesmo...

    String msg;
    User user;

    public FormattedMsg(String msg, User user) {
        this.msg = this.handleMsg(msg, user);
        this.user = user;
    }

    public String getFormattedMsg() {
        return this.msg;
    }

    private static boolean isValidColor(char code) {
        String validColors = "123456789abcdef";
        if (validColors.contains("" + code)) {
            return true;
        }
        return false;
    }

    private static boolean isValidSymbol(char code) {
        String validSymbols = "klmnor";
        if (validSymbols.contains("" + code)) {
            return true;
        }
        return false;
    }

    private String formatColorMsg(String msg) {
        String fmsg = "";
        for (int i = 0; i < msg.length(); i++) {
            if (i == msg.length() - 1) {
                fmsg = fmsg + msg.charAt(i);
            } else if (msg.charAt(i) == '&' && isValidColor(msg.charAt(i + 1))) {
                fmsg += '\u00A7';
            } else {
                fmsg = fmsg + msg.charAt(i);
            }
        }
        return fmsg;
    }

    private String formatSymbolMsg(String msg) {
        String fmsg = "";
        for (int i = 0; i < msg.length(); i++) {
            if (i == msg.length() - 1) {
                fmsg = fmsg + msg.charAt(i);
            } else if (msg.charAt(i) == '&' && isValidSymbol(msg.charAt(i + 1))) {
                fmsg += '\u00A7';
            } else {
                fmsg = fmsg + msg.charAt(i);
            }
        }
        return fmsg;
    }

    private String handleMsg(String msg, User user) {
        Boolean chatColorPermision = user.getCachedData().getPermissionData().checkPermission("gchat.text.color")
                .asBoolean();
        Boolean chatSymbolPermision = user.getCachedData().getPermissionData().checkPermission("gchat.text.symbol")
                .asBoolean();

        if (msg.contains("&") && chatColorPermision) {
            msg = formatColorMsg(msg);
        }
        if (msg.contains("&") && chatSymbolPermision) {
            msg = formatSymbolMsg(msg);
        }

        return msg;
    }

}

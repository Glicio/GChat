package utils;

public class FormattedText {
    // formata o texto para incluir cores e simbolos do minecraft
    String text;

    public FormattedText(String text) {
        if (text == null) {
            this.text = "";
        } else {
            this.text = this.handletext(text);
        }

    }

    public String getFormattedTexString() {

        return this.text;
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

    private String formatColortext(String text) {
        String ftext = "";
        for (int i = 0; i < text.length(); i++) {
            if (i == text.length() - 1) {
                ftext = ftext + text.charAt(i);
            } else if (text.charAt(i) == '&' && isValidColor(text.charAt(i + 1))) {
                ftext += '\u00A7';
            } else {
                ftext = ftext + text.charAt(i);
            }
        }
        return ftext;
    }

    private String formatSymboltext(String text) {
        String ftext = "";
        for (int i = 0; i < text.length(); i++) {
            if (i == text.length() - 1) {
                ftext = ftext + text.charAt(i);
            } else if (text.charAt(i) == '&' && isValidSymbol(text.charAt(i + 1))) {
                ftext += '\u00A7';
            } else {
                ftext = ftext + text.charAt(i);
            }
        }
        return ftext;
    }

    private String handletext(String text) {

        if (text.contains("&")) {
            text = formatColortext(text);
            text = formatSymboltext(text);
            text += "\u00A7r";
        }

        return text;
    }

}

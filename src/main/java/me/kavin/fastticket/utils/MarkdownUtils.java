package me.kavin.fastticket.utils;

public class MarkdownUtils {

    public static String escapeString(String string) {
        return string.replace("\\", "\\\\").replace("<", "\\<").replace(">", "\\>").replace("`", "\\`")
                .replace("*", "\\*").replace("{", "\\{").replace("}", "\\}").replace("[", "\\[").replace("]", "\\]")
                .replace("(", "\\(").replace(")", "\\)").replace("#", "\\#").replace("+", "\\+").replace("-", "\\-")
                .replace(".", "\\.").replace("!", "\\!").replace("_", "\\_").replace("\"", "\\\"").replace("$", "\\$")
                .replace("%", "\\%").replace("&", "\\&").replace("'", "\\'").replace(",", "\\,").replace("/", "\\/")
                .replace(":", "\\:").replace(";", "\\;").replace("=", "\\=").replace("?", "\\?").replace("@", "\\@")
                .replace("^", "\\^").replace("\n", "\\n").replace("\r", "\\r").replace("|", "\\|");
    }
}

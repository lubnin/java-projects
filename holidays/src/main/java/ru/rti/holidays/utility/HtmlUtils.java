package ru.rti.holidays.utility;

/**
 * Class Helper for constructing HTML code fragments
 */
public class HtmlUtils {
    /**
     * Generated html code
     */
    private String html = GlobalConstants.EMPTY_STRING;

    public class HtmlUtilsBuilder {
        private HtmlUtilsBuilder() {
            // Just to make constructor private
        }

        public HtmlUtilsBuilder withMessage(String message) {
            if (message == null) {
                return this; // don't allow to place null value inside the instance
            }
            HtmlUtils.this.html = message;
            return this;
        }

        public HtmlUtilsBuilder wrapBold() {
            HtmlUtils.this.html = bold(HtmlUtils.this.html);
            return this;
        }

        public HtmlUtilsBuilder wrapSpan(String cssClass) {
            HtmlUtils.this.html = span(HtmlUtils.this.html, cssClass);
            return this;
        }

        public HtmlUtils build() {
            HtmlUtils htmlUtilsInstance = new HtmlUtils();
            htmlUtilsInstance.html = HtmlUtils.this.html;
            return htmlUtilsInstance;
        }
    }

    public static HtmlUtilsBuilder newBuilder() {
        return new HtmlUtils().new HtmlUtilsBuilder();
    }

    public String getHTML() {
        return html;
    }

    public static String bold(String message) {
        if (CommonUtils.checkIfEmpty(message)) {
            message = GlobalConstants.EMPTY_STRING;
        }

        return String.format("<b>%s</b>", message);
    }

    public static String span(String message) {
        if (CommonUtils.checkIfEmpty(message)) {
            message = GlobalConstants.EMPTY_STRING;
        }

        return String.format("<span>%s</span>", message);
    }

    public static String span(String message, String cssClass) {
        if (CommonUtils.checkIfEmpty(message)) {
            message = GlobalConstants.EMPTY_STRING;
        }
        if (CommonUtils.checkIfEmpty(cssClass)) {
            cssClass = GlobalConstants.EMPTY_STRING;
        }
        return String.format("<span class=\"%s\">%s</span>", cssClass, message);
    }
}

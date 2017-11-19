package ru.rti.holidays.simple;

import org.junit.Test;
import ru.rti.holidays.utility.HtmlUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlUtilsTest {
    @Test
    public void testBoldMessageWrappedWithClassSpan() {
        assertThat(HtmlUtils.newBuilder()
                .withMessage("test message")
                .wrapBold()
                .wrapSpan("myClass")
                .build()
                .getHTML())
                .isEqualTo("<span class=\"myClass\"><b>test message</b></span>");
    }

    @Test
    public void testBoldMessageWrappedWithNullClassSpan() {
        assertThat(HtmlUtils.newBuilder()
                .withMessage("test message")
                .wrapBold()
                .wrapSpan(null)
                .build()
                .getHTML())
                .isEqualTo("<span class=\"\"><b>test message</b></span>");
    }

    @Test
    public void testBoldMessage() {
        assertThat(HtmlUtils.newBuilder()
                .withMessage("test message")
                .wrapBold()
                .build()
                .getHTML())
                .isEqualTo("<b>test message</b>");
    }

    @Test
    public void testMessage() {
        assertThat(HtmlUtils.newBuilder()
                .withMessage("test message")
                .build()
                .getHTML())
                .isEqualTo("test message");
    }

    @Test
    public void testMessageEmpty() {
        assertThat(HtmlUtils.newBuilder()
                .build()
                .getHTML())
                .isEqualTo("");
    }

    @Test
    public void testMessageNull() {
        assertThat(HtmlUtils.newBuilder()
                .withMessage(null)
                .build()
                .getHTML())
                .isEqualTo("");
    }

    @Test
    public void testMessageNullWrappedBold() {
        assertThat(HtmlUtils.newBuilder()
                .withMessage(null)
                .wrapBold()
                .build()
                .getHTML())
                .isEqualTo("<b></b>");
    }
}

package com.lostnfound.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class HtmlUtilsTest {

    @Test
    public void nullReturnsEmptyString() {
        assertEquals("", HtmlUtils.e(null));
    }

    @Test
    public void plainTextPassesThrough() {
        assertEquals("Hello World", HtmlUtils.e("Hello World"));
    }

    @Test
    public void ampersandEscaped() {
        assertEquals("Tom &amp; Jerry", HtmlUtils.e("Tom & Jerry"));
    }

    @Test
    public void lessThanEscaped() {
        assertEquals("&lt;script&gt;", HtmlUtils.e("<script>"));
    }

    @Test
    public void doubleQuoteEscaped() {
        assertEquals("say &quot;hi&quot;", HtmlUtils.e("say \"hi\""));
    }

    @Test
    public void singleQuoteEscaped() {
        assertEquals("it&#x27;s", HtmlUtils.e("it's"));
    }

    @Test
    public void xssPayloadFullyEscaped() {
        String payload = "<script>alert('xss')</script>";
        String escaped = HtmlUtils.e(payload);
        assertFalse(escaped.contains("<"));
        assertFalse(escaped.contains(">"));
        assertFalse(escaped.contains("'"));
    }

    @Test
    public void ampersandEscapedBeforeOthers() {
        // "&lt;" should not double-escape to "&amp;lt;"
        assertEquals("&amp;lt;", HtmlUtils.e("&lt;"));
    }
}

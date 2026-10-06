package com.universidad.torneosesports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UrlValidatorTest {
    @Test public void addsHttpsWhenSchemeIsMissing() {
        assertEquals("https://example.com", UrlValidator.normalize("example.com"));
    }

    @Test public void keepsValidHttpsUrl() {
        assertEquals("https://example.com/events", UrlValidator.normalize("https://example.com/events"));
    }

    @Test public void rejectsUnsafeOrMalformedUrls() {
        assertNull(UrlValidator.normalize("javascript:alert(1)"));
        assertNull(UrlValidator.normalize("not-a-host"));
        assertNull(UrlValidator.normalize(""));
    }
}

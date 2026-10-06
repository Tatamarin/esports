package com.universidad.torneosesports;

import java.net.URI;

public final class UrlValidator {
    private UrlValidator() {}

    public static String normalize(String value) {
        if (value == null) return null;
        String candidate = value.trim();
        if (!candidate.contains("://")) candidate = "https://" + candidate;
        try {
            URI uri = new URI(candidate);
            String scheme = uri.getScheme();
            if (("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme))
                    && uri.getHost() != null && uri.getHost().contains(".")) {
                return uri.toString();
            }
        } catch (Exception ignored) {
            // Invalid user input is reported by the caller.
        }
        return null;
    }
}

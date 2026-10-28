package com.bookingnest.helper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;

@Component
@NoArgsConstructor
public class SlugGenerator {

    public String generate(String name) {
        if (name == null || name.isBlank()) {
            return "";
        }
        return Normalizer.normalize(name.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")            // strip accents
                .toLowerCase(Locale.ROOT)                             // lower-case BEFORE the character filter
                .replaceAll("[^a-z0-9]+", "-")       // anything else becomes a hyphen
                .replaceAll("^-|-$", "");            // remove leading/trailing hyphen
    }
}

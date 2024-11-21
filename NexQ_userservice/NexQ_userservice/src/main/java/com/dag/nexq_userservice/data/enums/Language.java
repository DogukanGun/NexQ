package com.dag.nexq_userservice.data.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Language {
    English("eng"),
    Turkish("tr"),
    German("de");

    final String shortForm;

    Language(String shortForm) {
        this.shortForm = shortForm;
    }

    public static List<String> getAllShortForms() {
        return Arrays.stream(Language.values())
                .map(language -> language.shortForm)
                .collect(Collectors.toList());
    }
}

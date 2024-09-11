package com.example.projectManagement.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    InProgress("InProgress","در حال انجام"),
    Completed("Completed","تکمیل شده"),
    Canceled("لغو شده","Canceled");

    private final String english;
    private final String persian;

    public String getTranslation(String language) {
        return switch (language.toLowerCase()) {
            case "english" -> getEnglish();
            case "persian" -> getPersian();
            default -> throw new IllegalArgumentException("Unsupported language: " + language);
        };
    }

    @Override
    public String toString() {
        return english + " / " + persian;
    }
}

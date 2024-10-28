package com.example.projectManagement.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {

    MALE("Male", "مرد"),
    FEMALE("Female", "زن");

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
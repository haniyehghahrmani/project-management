package com.example.projectManagement.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogType {

    INFO("Information", "اطلاعات"),
    ERROR("Error", "خطا"),
    DEBUG("Debug", "رفع اشکال‌"),
    OPERATION("Operation", "عملیات");

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

package com.example.dataprocessor;

import java.util.List;
import java.util.stream.Collectors;

public class TransformProcessor {
    
    @DataProcessor(name = "Преобразование в верхний регистр", priority = 1)
    public List<String> toUpperCase(List<String> data) {
        return data.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
    
    @DataProcessor(name = "Добавление префикса", priority = 2)
    public List<String> addPrefix(List<String> data) {
        return data.stream()
                .map(s -> "[PROCESSED] " + s)
                .collect(Collectors.toList());
    }
    
    @DataProcessor(name = "Обрезка пробелов", priority = 3)
    public List<String> trimSpaces(List<String> data) {
        return data.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

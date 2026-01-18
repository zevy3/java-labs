package com.example.dataprocessor;

import java.util.List;
import java.util.stream.Collectors;

public class AggregationProcessor {
    
    @DataProcessor(name = "Группировка по длине", priority = 1)
    public List<String> groupByLength(List<String> data) {
        return data.stream()
                .collect(Collectors.groupingBy(String::length))
                .entrySet()
                .stream()
                .map(entry -> "Длина " + entry.getKey() + ": " + entry.getValue().size() + " элементов")
                .collect(Collectors.toList());
    }
    
    @DataProcessor(name = "Подсчет статистики", priority = 2)
    public List<String> calculateStatistics(List<String> data) {
        long count = data.size();
        double avgLength = data.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0.0);
        
        int maxLength = data.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
        
        return List.of(
            "Статистика:",
            "Всего элементов: " + count,
            "Средняя длина: " + String.format("%.2f", avgLength),
            "Максимальная длина: " + maxLength
        );
    }
    
    @DataProcessor(name = "Уникальные значения", priority = 3)
    public List<String> getUniqueValues(List<String> data) {
        return data.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}

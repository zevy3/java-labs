package com.example.dataprocessor;

import java.util.List;
import java.util.stream.Collectors;

public class FilterProcessor {
    
    @DataProcessor(name = "Фильтр длинных строк", priority = 1)
    public List<String> filterLongStrings(List<String> data) {
        return data.stream()
                .filter(s -> s.length() > 5)
                .collect(Collectors.toList());
    }
    
    @DataProcessor(name = "Фильтр строк с цифрами", priority = 2)
    public List<String> filterStringsWithNumbers(List<String> data) {
        return data.stream()
                .filter(s -> s.matches(".*\\d.*"))
                .collect(Collectors.toList());
    }
    
    @DataProcessor(name = "Фильтр непустых строк", priority = 3)
    public List<String> filterNonEmpty(List<String> data) {
        return data.stream()
                .filter(s -> !s.trim().isEmpty())
                .collect(Collectors.toList());
    }
}

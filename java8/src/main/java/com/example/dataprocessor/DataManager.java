package com.example.dataprocessor;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataManager {
    private List<String> data;
    private List<Object> processors;
    private ExecutorService executorService;
    
    public DataManager() {
        this.data = new ArrayList<>();
        this.processors = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(4);
    }
    
    public void registerDataProcessor(Object processor) {
        System.out.println("Регистрация обработчика: " + processor.getClass().getSimpleName());
        processors.add(processor);
    }
    
    public void loadData(String source) throws IOException {
        System.out.println("Загрузка данных из: " + source);
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            data = reader.lines().collect(Collectors.toList());
        }
        System.out.println("Загружено строк: " + data.size());
    }
    
    public void processData() throws Exception {
        System.out.println("\n=== Начало обработки данных ===");
        
        List<Future<List<String>>> futures = new ArrayList<>();
        
        for (Object processor : processors) {
            Method[] methods = processor.getClass().getDeclaredMethods();
            
            for (Method method : methods) {
                if (method.isAnnotationPresent(DataProcessor.class)) {
                    DataProcessor annotation = method.getAnnotation(DataProcessor.class);
                    
                    Future<List<String>> future = executorService.submit(() -> {
                        System.out.println("Поток " + Thread.currentThread().getName() + 
                                         " обрабатывает: " + annotation.name());
                        
                        try {
                            @SuppressWarnings("unchecked")
                            List<String> result = (List<String>) method.invoke(processor, data);
                            System.out.println("Завершена обработка: " + annotation.name() + 
                                             ", результат: " + result.size() + " элементов");
                            return result;
                        } catch (Exception e) {
                            System.err.println("Ошибка в методе " + annotation.name() + ": " + e.getMessage());
                            return new ArrayList<>();
                        }
                    });
                    
                    futures.add(future);
                }
            }
        }
        
        List<String> allResults = new ArrayList<>();
        for (Future<List<String>> future : futures) {
            try {
                allResults.addAll(future.get());
            } catch (Exception e) {
                System.err.println("Ошибка получения результата: " + e.getMessage());
            }
        }
        
        data = allResults;
        System.out.println("=== Обработка завершена, всего результатов: " + data.size() + " ===\n");
    }
    
    public void saveData(String destination) throws IOException {
        System.out.println("Сохранение данных в: " + destination);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
        System.out.println("Сохранено строк: " + data.size());
    }
    
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
    
    public List<String> getData() {
        return new ArrayList<>(data);
    }
}

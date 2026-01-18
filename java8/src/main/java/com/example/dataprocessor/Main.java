package com.example.dataprocessor;

public class Main {
    public static void main(String[] args) {
        try {
            DataManager dataManager = new DataManager();
            
            dataManager.registerDataProcessor(new FilterProcessor());
            dataManager.registerDataProcessor(new TransformProcessor());
            dataManager.registerDataProcessor(new AggregationProcessor());
            
            dataManager.loadData("data/input.txt");
            
            dataManager.processData();
            
            dataManager.saveData("data/output.txt");
            
            dataManager.shutdown();
            
            System.out.println("\nПриложение успешно завершено!");
            
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

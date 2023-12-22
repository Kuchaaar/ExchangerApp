package com.exchanger.ExchangerApp.config;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class AbstractSpringDocTest{
    public static String getContent(String fileName) {
        try {
            Path path = Paths.get(Objects.requireNonNull(AbstractSpringDocTest.class.getClassLoader()
                    .getResource(fileName)).toURI());
            byte[] fileBytes = Files.readAllBytes(path);
            return new String(fileBytes, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }
}

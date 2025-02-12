package com.example.fitnessapp.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {
    @Override
    public Double convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        String normalized = source.replace(',', '.');
        return Double.parseDouble(normalized);
    }
}

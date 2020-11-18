package de.neuefische.finalproject.ohboy.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdUtils {
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}

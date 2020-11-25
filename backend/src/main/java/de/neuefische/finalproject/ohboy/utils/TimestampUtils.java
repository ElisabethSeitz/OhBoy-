package de.neuefische.finalproject.ohboy.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TimestampUtils {
    public Instant generateTimeStampEpochSeconds() {
        return Instant.ofEpochSecond(Instant.now().getEpochSecond());
    }
}

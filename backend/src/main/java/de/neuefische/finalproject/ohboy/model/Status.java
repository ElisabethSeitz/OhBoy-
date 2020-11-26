package de.neuefische.finalproject.ohboy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    @JsonProperty("OPEN")
    OPEN,
    @JsonProperty("DONE")
    DONE
}

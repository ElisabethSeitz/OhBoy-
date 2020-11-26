package de.neuefische.finalproject.ohboy.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("OPEN")
    OPEN,
    @JsonProperty("DONE")
    DONE
}

package com.meda.titu.medicalclinicapplication.entity.ct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InterpretationStatus {
    CREATED("CREATED"),
    UPDATED("UPDATED"),
    TO_DIAGNOSE("TO_DIAGNOSE"),
    DIAGNOSED("DIAGNOSED");

    private final String value;

    InterpretationStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static InterpretationStatus fromValue(String value) {
        for (InterpretationStatus status : InterpretationStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid interpretation status: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean hasNext(InterpretationStatus newStatus) {
        return this.ordinal() + 1 == newStatus.ordinal();
    }
}

package com.example.appdai.model;

/**
 * PC_type Enum
 * This enum is used to represent the type of Photocard.
 * A Photocard can be :
 * - PCA : Photocard from an album
 * - POB : Photocard from a pre-order benefit or a lucky draw
 * - PCO : Photocard from an official source
 * - PCE : Photocard from an event
 */
public enum PC_type {
    PCA,
    POB,
    PCO,
    PCE;

    @Override
    public String toString() {
        return this.name();
    }

    public static PC_type fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("PC_type cannot be null or empty");
        }
        try {
            return PC_type.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid PC_type: " + value);
        }
    }
}

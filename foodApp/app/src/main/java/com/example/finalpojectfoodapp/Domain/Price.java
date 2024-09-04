package com.example.finalpojectfoodapp.Domain;

public class Price {
    private int Id;
    private String Value;

    public Price() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return Value;
    }
}


package com.erdemserhat.databasephoto;

import java.io.Serializable;

public class Vocabulary implements Serializable {
    private String name;
    private String meaning;
    private byte[] photo;

    public Vocabulary(String name, String meaning, byte[] photo) {
        this.name = name;
        this.meaning = meaning;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}

package com.example.mudit.picscramble.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mudit on 31/7/16.
 */
public class Media {

    @JsonProperty("m")
    private String m;

    /**
     *
     * @return
     * The m
     */
    @JsonProperty("m")
    public String getM() {
        return m;
    }

    /**
     *
     * @param m
     * The m
     */
    @JsonProperty("m")
    public void setM(String m) {
        this.m = m;
    }

}
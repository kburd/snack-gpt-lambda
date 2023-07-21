package com.kburd.snackgpt.models;

import lombok.Data;

@Data
public class Recipe {
    private String name;
    private String[] ingredients;
    private String[] steps;
}

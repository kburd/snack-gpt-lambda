package com.kburd.snackgpt.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SnackGptResponse {
    private Boolean recipeFound;
    private Recipe recipe;
    private String exception;
}

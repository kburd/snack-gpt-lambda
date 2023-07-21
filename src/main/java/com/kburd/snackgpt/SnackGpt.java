package com.kburd.snackgpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kburd.snackgpt.models.Recipe;
import com.kburd.snackgpt.models.RecipeRequest;
import com.kburd.snackgpt.models.SnackGptResponse;

public class SnackGpt {

    private final static ObjectMapper mapper = new ObjectMapper();

    public SnackGptResponse getRecipe(RecipeRequest recipeRequest) {

        String prompt, openAiResponse;
        SnackGptResponse response;

        try {
            prompt = buildPrompt(recipeRequest.getQuery());
            openAiResponse = OpenAiClient.promptOpenAI(prompt);
            response = SnackGptResponse.builder()
                    .recipeFound(true)
                    .recipe(mapper.readValue(openAiResponse, Recipe.class))
                    .build();

        }
        catch (Exception e){
            response = SnackGptResponse.builder()
                    .recipeFound(false)
                    .exception(e.getMessage())
                    .build();
        }

        return response;

    }

    private String buildPrompt(String query){
        return "Generate a recipe for '" + query + "' in the following json format: " +
                "{name: string, ingredients: string[], steps: string[]}";
    }

}

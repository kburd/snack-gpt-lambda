package com.kburd.snackgpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SnackGpt {

    public Recipe getRecipe(RecipeRequest recipeRequest) throws Exception {

        String prompt, response;
        Recipe recipe;

        prompt = buildPrompt(recipeRequest.getQuery());
        response = OpenAiClient.promptOpenAI(prompt);
        recipe = mapResponseToRecipe(response);

        return recipe;

    }

    private String buildPrompt(String query){
        return "Generate a recipe for '" + query + "' in the following json format: " +
                "{name: string, ingredients: string[], steps: string[]}";
    }

    private Recipe mapResponseToRecipe(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, Recipe.class);
    }

}

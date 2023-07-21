package com.kburd.snackgpt;

import org.json.JSONObject;

public class SnackGpt {

    public String getRecipe(JSONObject recipeRequest) {

        String query, prompt, response;

        try {
            query = recipeRequest.getString("query");
            prompt = buildPrompt(query);
            response = OpenAiClient.promptOpenAI(prompt);
        }
        catch (Exception e){
            response = e.toString();
        }

        return response;

    }

    private String buildPrompt(String query){
        return "Generate a recipe for '" + query + "' in the following json format: " +
                "{name: string, ingredients: string[], steps: string[]}";
    }

}

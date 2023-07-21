package com.kburd.snackgpt;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class OpenAiClient {

    static final private String API_KEY = "TODO";
    static final private String MODEL_ID = "gpt-3.5-turbo";

    public static String promptOpenAI(String prompt) {

        StringBuilder responseString = new StringBuilder();

        // Create an HttpClient instance
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create a POST request to the OpenAI API endpoint
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/chat/completions");

        // Set the request headers
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + API_KEY);

        // Set the request body
        String requestBody = "{" +
                "\"model\": \"" + MODEL_ID + "\"," +
                "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]," +
                "\"temperature\": 0.7" +
                "}";
        httpPost.setEntity(new StringEntity(requestBody, "UTF-8"));

        try {
            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // Get the response entity
            HttpEntity entity = response.getEntity();

            if (entity != null) {

                // Convert the response entity to a string
                JSONObject body = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
                JSONObject choiceObj;

                for(Object choice : body.getJSONArray("choices")){
                    choiceObj = (JSONObject) choice;
                    responseString.append(choiceObj.getJSONObject("message").getString("content")).append("\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the HttpClient
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return responseString.toString();

    }

}

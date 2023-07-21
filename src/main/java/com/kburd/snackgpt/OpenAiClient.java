package com.kburd.snackgpt;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.stream.Collectors;

public class OpenAiClient {

    public static String promptOpenAI(String prompt) throws Exception {

        StringBuilder output = new StringBuilder();

        try {

            URL url = new URL("https://api.openai.com/v1/chat/completions");
            String apiKey = AwsSecretsManagerClient.getSecret();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes("{" +
                    "\"model\": \"gpt-3.5-turbo\"," +
                    "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]," +
                    "\"temperature\": 0.7" +
                    "}");
            outputStream.flush();
            outputStream.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.lines().collect(Collectors.joining(""));
                reader.close();

                JSONObject body = new JSONObject(response);
                JSONObject choiceObj;
                for (Object choice : body.getJSONArray("choices")) {
                    choiceObj = (JSONObject) choice;
                    output.append(choiceObj.getJSONObject("message").getString("content")).append("\n");
                }

            } else {
                throw new Exception("Open AI Call Failed with Status ");
            }
        }

        catch (MalformedURLException | ProtocolException e){
            throw new Exception("Failed to Make Request to OpenAI");
        }

        catch (IOException e){
            throw new Exception(e.getMessage());
        }

        catch (NullPointerException e){
            throw new Exception("Failed to Parse Response from OpenAI");
        }

        return output.toString();

    }

}

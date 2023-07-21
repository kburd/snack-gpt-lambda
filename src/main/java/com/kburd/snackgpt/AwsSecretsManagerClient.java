package com.kburd.snackgpt;

 import software.amazon.awssdk.regions.Region;
 import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
 import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

public class AwsSecretsManagerClient {

    public static String getSecret() {

        String secretName = "OpenAiApiKey";
        Region region = Region.of("us-east-2");

        SecretsManagerClient client = SecretsManagerClient.builder().region(region).build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        try {
            return client.getSecretValue(getSecretValueRequest).secretString();
        } catch (Exception e) {
            throw e;
        }

    }
}

package com.dataai.common.config;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import okhttp3.*;

@Component
public class QwenService {

    @Value("${qwen.api-key}")
    private String apiKey;

    @Value("${qwen.api-url}")
    private String apiUrl;

    @Value("${qwen.model}")
    private String model;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    /**
     * 调用通义千问对话
     */
    public String chat(String message) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);

        JSONObject msg = new JSONObject();
        msg.put("role", "user");
        msg.put("content", message);
        requestBody.put("messages", new JSONObject[]{msg});

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody.toJSONString(),
                        MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("调用失败: " + response.code());
            }
            String body = response.body().string();
            JSONObject json = JSONObject.parseObject(body);
            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        }
    }
}
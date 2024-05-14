package com.aluracursos.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obtenerTraduccion(String texto) {
        OpenAiService service = new OpenAiService("sk-proj-H2ndmMjqdI5xyC7n51wNT3BlbkFJyp0hAipl8H67FP5YJRYl");

        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-16k")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
    }
}

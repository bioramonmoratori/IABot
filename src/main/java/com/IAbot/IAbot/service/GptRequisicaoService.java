package com.IAbot.IAbot.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class GptRequisicaoService {
	
	@Autowired
	TokenService tokenService;
	
	public String analiseCorpoDoEmail(String textoDoEmail, String filtro) throws Exception {
		
		String tokenGpt = tokenService.obterTokenGpt();
		String promptExtenso = "Leia o texto e diga se ele fala sobre " + filtro + ". Responda apenas com 'Sim' ou 'Não'. TEXTO: " + textoDoEmail;
		
		String prompt = promptExtenso.substring(0, Math.min(promptExtenso.length(), 800));
		JsonObject json = new JsonObject();
		json.addProperty("model", "text-davinci-003");
		json.addProperty("prompt", prompt);
		json.addProperty("temperature", 0);
		json.addProperty("max_tokens", 50);
        
		//Requisicao
		String url = "https://api.openai.com/v1/completions";
		
		URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        // Configurar a requisição HTTP POST
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + tokenGpt);
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        
        Gson gsonRequisicao = new Gson();
        String jsonString = gsonRequisicao.toJson(json);
        
        // Escreva a string JSON no corpo da requisição
        OutputStream os = con.getOutputStream();
        os.write(jsonString.getBytes());
        os.flush();
        os.close();
        
        //Obtendo a resposta da API do Chat-GPT
        int responseCode = con.getResponseCode();
        
        BufferedReader in = new BufferedReader(
        		new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        in.close();

        String resposta = response.toString();
        
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(resposta, JsonObject.class);

        String texto = jsonObject.getAsJsonArray("choices")
                .get(0)
                .getAsJsonObject()
                .get("text")
                .getAsString();
		
		return texto;
	}
	
}

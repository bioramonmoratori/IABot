package com.IAbot.IAbot.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class GmailRequisicaoService {
	
	@Autowired 
	TokenService tokenService;
	
	public List<String> listandoEmails() throws Exception{
		
		String urlGeral = "https://gmail.googleapis.com/gmail/v1/users/ramon.moratori@estudante.ufjf.br/messages";
		String url = urlGeral + "?access_token=" + tokenService.obterTokenGmail();
		
		URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        int responseCode = con.getResponseCode();
        System.out.println("Código de resposta: " + responseCode);
        
        BufferedReader in = new BufferedReader(
        		new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        String resposta = response.toString();
        
        // Converter a string JSON em um objeto JsonObject usando Gson
        Gson gson = new Gson();
        JsonObject obj1 = gson.fromJson(resposta, JsonObject.class);
        
        // Obter o array "messages" do objeto
        JsonArray messages = obj1.getAsJsonArray("messages");
        
        List<String> listaDeIds = new ArrayList<>();
        
        // Percorrer o array "messages" e adicionar objetos com o campo "id" ao novo array
        for (JsonElement element : messages) {
          JsonObject message = element.getAsJsonObject();
          if (message.has("id")) {
        	  String idDoElemento = message.get("id").toString();
        	  listaDeIds.add(idDoElemento);
          }
        }
        
        System.out.println(listaDeIds);
        
		return listaDeIds;
	}
	
	public String leituraDeEmailPeloId(String idDoEmail) throws Exception {
		
        String urlGeral = "https://gmail.googleapis.com/gmail/v1/users/" + tokenService.obterUrlEmail() + "/messages/";
        String url = urlGeral + idDoEmail + "?access_token=" + tokenService.obterTokenGmail();
        
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        int responseCode = con.getResponseCode();
        if(responseCode == 200) {
        	System.out.println("E-mail processado com sucesso!");
        } else {
        	System.out.println("E-mail não lido.");
        }
        
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

        String corpoDoEmail = jsonObject.get("snippet").getAsString();
        
		return corpoDoEmail;
		
	}
	
}

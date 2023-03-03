package com.IAbot.IAbot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FluxoGeralService {
	
	@Autowired
	GmailRequisicaoService gmailRequisicaoService;
	
	@Autowired
	GptRequisicaoService gptRequisicaoService;
	
	public String fluxoGeral() throws Exception {
		
		String assunto = "vagas de emprego";
		
		List<String> listaDeIdsDeEmails = gmailRequisicaoService.listandoEmails();
		
		List<String> listaDeTextos = new ArrayList<>();
		
		for (String idDoEmail : listaDeIdsDeEmails) {
			
			idDoEmail = idDoEmail.replace("\"", "");
			String textoDoEmail = gmailRequisicaoService.leituraDeEmailPeloId(idDoEmail);
			listaDeTextos.add(textoDoEmail);
		}
		
		int numeroDoEmail = 1;
		
		for(String textoDoEmail : listaDeTextos) {

			String resultadoBruto = gptRequisicaoService.analiseCorpoDoEmail(textoDoEmail, assunto);
			String resultado = "";
			if(resultadoBruto.contains("Sim") || resultadoBruto.contains("sim")) {
				resultado = "Sim, contém.";
			} else if(resultadoBruto.contains("Não") || resultadoBruto.contains("não") || resultadoBruto.contains("Nao") || resultadoBruto.contains("nao")) {
				resultado = "Não contém.";
			} else {
				
				resultado = "E-mail com formato muito diferente. Bugou minha cabeça x_x";
			}
			System.out.println("E-mail número " + numeroDoEmail + " contém o assunto \""+ assunto +"\"? " + resultado);
			System.out.println("--------------");
			numeroDoEmail ++;
			
			Thread.sleep(4000);
			
		}
		
		return "";
	}
	
}

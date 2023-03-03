package com.IAbot.IAbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IAbot.IAbot.service.FluxoGeralService;
import com.IAbot.IAbot.service.GmailRequisicaoService;
import com.IAbot.IAbot.service.GptRequisicaoService;
import com.IAbot.IAbot.service.TokenService;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

	@Autowired
	GmailRequisicaoService gmailRequisicaoService;

	@Autowired
	GptRequisicaoService gptRequisicaoService;
	
	@Autowired
	FluxoGeralService fluxoGeralService;

	@Autowired
	TokenService tokenService;

	@GetMapping("/listandoEmails")
	public String listandoEmails() throws Exception {

		

		return "";
	}

	@GetMapping("/lendoEmailPeloId")
	public String leituraDeEmail() throws Exception {

		fluxoGeralService.fluxoGeral();
		
		return "";

	}

	@GetMapping("/analisandoCorpoDoEmail")
	public String analiseTextoDoEmail() throws Exception {

		gptRequisicaoService.analiseCorpoDoEmail("Ola meu nome é Ramon", "Veja se tem um nome aqui");

		return "";
	}

}

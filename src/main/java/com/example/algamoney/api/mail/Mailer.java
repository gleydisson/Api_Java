package com.example.algamoney.api.mail;

import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Mailer {
		@Autowired
		private JavaMailSender mailSender;
		
		@EventListener
		private void teste(ApplicationReadyEvent event) {
			this.enviarEmail("financemoneyto@gmail.com", Arrays.asList("gleydisson@hotmail.com"), 
							 "Testando", "Ola!<br/>Teste Ok.");
			System.out.println("Terminado o envio de email");
		}
	// Ultima aula 22.17 10:00 min
		public void enviarEmail(String remetente, List<String> destinatarios,
								String assunto, String mensagem) {
			
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
				
				helper.setFrom(remetente);
				helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
				helper.setSubject(assunto);
				helper.setText(mensagem, true);
				
				mailSender.send(mimeMessage);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Problems with sending e-mail");
			}
		}
}

package com.example.linksShortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final LinkRepository repository;

	@Autowired
	public DatabaseLoader(LinkRepository repository) {
		this.repository = repository;
	}

	public void run(String... strings) throws Exception {

		// TODO
		// добавить несколько попыток генерации коротких ссылок

		this.repository.save(new Link(getShortUrl(), "ya.ru"));
		this.repository.save(new Link(getShortUrl(), "google.com"));
	}

	private String getShortUrl() {
		String possibleCharacters = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
		StringBuilder idBuilder = new StringBuilder();
		Random rnd = new Random();
		while (idBuilder.length() < 5) {
			int index = (int) (rnd.nextFloat() * possibleCharacters.length());
			idBuilder.append(possibleCharacters.charAt(index));
		}

		return idBuilder.toString();
	}
}
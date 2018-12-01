package com.example.linksShortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final LinkRepository repository;

	@Autowired
	public DatabaseLoader(LinkRepository repository) {
		this.repository = repository;
	}

	public void run(String... strings) throws Exception {

		this.repository.save(new Link("123", "ya.ru"));
		this.repository.save(new Link("456", "google.com"));
	}
}
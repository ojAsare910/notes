package com.ojasare.notes;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class NotesApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(e -> {
			if (System.getProperty(e.getKey()) == null) {
				System.setProperty(e.getKey(), e.getValue());
			}
		});
		SpringApplication.run(NotesApplication.class, args);
	}

}

package com.ojasare.notes;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotesApplicationTests {

	@BeforeAll
	static void loadEnv() {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(e -> {
			if (System.getProperty(e.getKey()) == null) {
				System.setProperty(e.getKey(), e.getValue());
			}
		});
	}

	@Test
	void contextLoads() {
	}

}

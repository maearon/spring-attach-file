package com.example.springboilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpringBoilerplateApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
							.filename(".env") // nếu file không phải tên mặc định
							.ignoreIfMissing()
							.load();

		// Load biến môi trường vào System nếu cần
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(SpringBoilerplateApplication.class, args);
	}

}

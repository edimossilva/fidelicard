package com.loop.fidelicard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loop.fidelicard.model.Stamp;
import com.loop.fidelicard.repository.StampRepository;

@SpringBootApplication
public class FidelicardApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(FidelicardApplication.class, args);
	}

	
}

package org.dtodo1paco.samples.urlshortener;

import org.dtodo1paco.samples.urlshortener.repository.ServiceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {
	@Autowired
	private ServiceUserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}

}
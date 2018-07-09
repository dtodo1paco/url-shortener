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

//	@Override
//	public void run(String... args) throws Exception {
//		ServiceUser defaultAdmin = userRepository.findByUserName("dtodo1paco");
//		if (defaultAdmin == null) {
//			defaultAdmin = userRepository.save(new ServiceUser("dtodo1paco",
//				"mi.123456", UserConstants.ROLE_ADMIN, "Paco Alías"));
//		}
//		System.out.println("ADMIN " + defaultAdmin);
//	}
}
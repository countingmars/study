package io.github.countingmars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringRedisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}

	@Autowired
	RedisMessageService service;

	@Override
	public void run(String... strings) throws Exception {
		service.set(newMessage("first"));
		System.out.println(service.get());
		System.out.println(service.cache(1));

		service.set(newMessage("second"));
		System.out.println(service.get());
		System.out.println(service.cache(2));
	}

	private Message newMessage(String id) {
		return new Message(id);
	}
}

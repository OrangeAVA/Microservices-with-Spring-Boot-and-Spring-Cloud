package com.example.demo;

import org.springframework.boot.ApplicationArguments;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBasicMavenApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBasicMavenApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("displaying the command line arguments");
		for (String arg : args.getOptionNames()) {
			System.out.println(arg);
		}
		
		System.out.println(args.getOptionValues("application.name"));
		System.out.println(args.getOptionValues("server.port"));
		System.out.println("the non option argument values are:");
		args.getNonOptionArgs().forEach(System.out::println);
		
	}

}



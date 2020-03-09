package com.test.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Class CustomerStatementApplication.
 */
@SpringBootApplication
@EnableScheduling
public class CustomerStatementApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CustomerStatementApplication.class, args);
	}

}

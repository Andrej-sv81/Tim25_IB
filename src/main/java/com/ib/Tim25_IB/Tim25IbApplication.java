package com.ib.Tim25_IB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Tim25IbApplication {
	public static void main(String[] args) {

		SpringApplication.run(Tim25IbApplication.class, args);
	}

}

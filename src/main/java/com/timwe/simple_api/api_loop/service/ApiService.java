package com.timwe.simple_api.api_loop.service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class ApiService {

	Logger log = Logger.getLogger(ApiService.class.getName());

	private String queueName = "TEST-QUEUE";
	
	@Value("${hostname}")
	private String hostName;
	
	public void produce() throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(hostName);
		factory.setPort(5672);
		factory.setPassword("guest");
		factory.setUsername("guest");
		factory.setVirtualHost("/");

		try (
				Connection connection = factory.newConnection(); 
				Channel channel = connection.createChannel()
		) {

			channel.queueDeclare(queueName, false, false, false, null);
			String message = "";

			for (int i = 0; i < 60; i++) {
				message = "UUID = " + UUID.randomUUID() + " TEST QUEUE";
				channel.basicPublish("", queueName, null, message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");
			}

		}

	}

	public void executeLoop(int repetitions) {
		for (int i = 0; i < repetitions; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void fatorial(int number) {
		int i, fact = 1;
		for (i = 1; i <= number; i++) {
			fact = fact * i;
			log.info(Thread.currentThread().getName() + " exec -> " + i);
		}
		log.info("Factorial of " + number + " is: " + fact);
	}

}
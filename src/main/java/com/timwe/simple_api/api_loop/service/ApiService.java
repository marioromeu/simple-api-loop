package com.timwe.simple_api.api_loop.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class ApiService {

	Logger log = Logger.getLogger(ApiService.class.getName());
	
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
		int i,fact=1;		
		for(i=1;i<=number;i++){
			fact=fact*i;
			log.info(Thread.currentThread().getName() + " exec -> "+ i);
		}
		log.info("Factorial of "+number+" is: "+fact);
	}

}
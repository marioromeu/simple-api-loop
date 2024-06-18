package com.timwe.simple_api.api_loop.service;

import org.springframework.stereotype.Service;

@Service
public class ApiService {

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
			System.out.println(i);
		}
		System.out.println("Factorial of "+number+" is: "+fact);
	}

}
package com.timwe.simple_api.api_loop.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.timwe.simple_api.api_loop.service.ApiService;
import com.timwe.simple_api.api_loop.service.RabbitMQConsumerService;
import com.timwe.simple_api.api_loop.service.RabbitMQProducerService;

@RestController
public class ApiController {

    private final ApiService apiService;
    
    private final RabbitMQConsumerService consumerService;
    
    private final RabbitMQProducerService producerService;

    public ApiController(
    		ApiService apiService, 
    		RabbitMQConsumerService consumerService,
    		RabbitMQProducerService producerService) {
        this.apiService = apiService;
        this.consumerService = consumerService;
        this.producerService = producerService;
    }

    @GetMapping("/start-consumer")
    public String startConsumer() throws IOException {
   		consumerService.startConsumer();
        return "Consumidor iniciado";
    }
    
    @GetMapping("/produce")
    public String produce() {
    	try {
    		producerService.produce();
		} catch (IOException e) {
			e.printStackTrace();
			return "Message failed";			
		} catch (TimeoutException e) {
			e.printStackTrace();
			return "Message failed";			
		}
        return "Message produced";
    }

    @GetMapping("/execute/{repetitions}")
    public String executeLoop(@PathVariable String repetitions) {
    	Integer intValue = Integer.parseInt(repetitions);
        apiService.executeLoop(intValue);
        return "Loop executed " + intValue + " times with 100ms sleep.";
    }
    
    @GetMapping("/fatorial/{number}")
    public String fatorialLoop(@PathVariable String number) {
    	Integer intValue = Integer.parseInt(number);
        apiService.fatorial(intValue);
        return "fatorial " + intValue + " executed";
    }
    
    
}
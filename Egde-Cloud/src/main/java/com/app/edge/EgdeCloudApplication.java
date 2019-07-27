package com.app.edge;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy

@SpringBootApplication
public class EgdeCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgdeCloudApplication.class, args);
	}

}

@Data
class Item{
	private String Name;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
		
}

@FeignClient("ItemCatalog-Service")
interface ItemClient{
	@GetMapping("/Items")
	Resources<Item> ReadItem();
}

@RestController
class GoodItemApiAdapterRestController{
	private final ItemClient itemClient;

	public GoodItemApiAdapterRestController(ItemClient itemClient) {
		//super();
		this.itemClient = itemClient;
	}
	
	@GetMapping("/top-brands")
	public Collection<Item>goodItem(){
		return itemClient.ReadItem()
				.getContent()
				.stream()
				.filter(this::IsGreat).collect(Collectors.toList());
				
	}
	
	private Boolean IsGreat(Item item) {
		return !item.getName().equals("Nike") &&
                !item.getName().equals("Adidas") &&
                !item.getName().equals("Reebok");
	}
	
}

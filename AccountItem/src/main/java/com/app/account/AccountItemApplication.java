package com.app.account;

import java.util.stream.Stream;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.annotation.Id;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SpringBootApplication
public class AccountItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountItemApplication.class, args);
	}
	
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity

class Account{
	public Account(String vor, String nach, String Alias) {
		// TODO Auto-generated constructor stub
		this.Vorname=vor;
		this.Nachname= nach;
		this.Aliasname= Alias;
	}
	public Account(String account) {
		// TODO Auto-generated constructor stub
		this.Vorname=account;
	}
	@Id
	@GeneratedValue
	private Long id;
	
	private String Vorname;
	private String Nachname;
	private String Aliasname;
	
}

@RepositoryRestResource
interface AccountRepository extends JpaRepository<Account, Long>{
	
}

@Component
class AccountInitializer implements CommandLineRunner{
	
	private final AccountRepository accountRepository;
	
	

	public AccountInitializer(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Stream.of("Baster","Alane","Killay Dj","Empereur")
		.forEach( Account -> accountRepository.save(new Account(Account)));
		
		accountRepository.findAll().forEach(System.out::println);
	}
}
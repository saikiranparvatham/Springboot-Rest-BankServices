package com.mmbank.app.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mmbank.app.accounts.Account;
import com.mmbank.app.repo.AccountRepository;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

	@Autowired
	AccountRepository repository;
	
	
	@PostMapping
	public void addNewAccount(@RequestBody Account account) {
		repository.save(account);
	}
	
	@GetMapping
	public List<Account> getAllAccounts(){
		return repository.findAll();
	}
	
	@PutMapping
	public void updateEmployee(@RequestBody Account account) {
		repository.save(account);
	}
	
	@GetMapping("/{accountNumber}")
	public Optional<Account> findById(@PathVariable int accountNumber)
	{
		return repository.findById(accountNumber);
	}
	
	@GetMapping("/{accountNumber}/balance")
	public double getCurrentBalance(@PathVariable int accountNumber)
	{
		 Optional<Account> account=repository.findById(accountNumber);
		 double balance=account.get().getCurrentBalance();
		 return balance;
	}
	
}

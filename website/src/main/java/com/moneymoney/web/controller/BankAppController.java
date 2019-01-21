package com.moneymoney.web.controller;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.moneymoney.web.entity.Transaction;

@Controller
public class BankAppController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/withdrawForm")
	public String depositForm() {
		return "WithdrawForm";
	}
	@RequestMapping("/withdraw")
	public String deposit(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://localhost:8090/transactions/withdraw", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "WithdrawForm";
	}
	
	@RequestMapping("/depositForm")
	public String withdrawForm() {
		return "DepositForm";
	}
	
	@RequestMapping("/deposit")
	public String withdraw(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://localhost:8090/transactions/deposit", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "DepositForm";
	}
	
	@RequestMapping("/fundtransferForm")
	public String fundtransferForm() {
		return "FundTransferForm";
	}
	
	@RequestMapping("/fundtransfer")
	public String fundtransfer(@RequestParam("senderaccountNumber")int senderaccountNumber,@RequestParam("receiveraccountNumber")int receiveraccountNumber,@ModelAttribute Transaction transaction,
			Model model) {
		transaction.setAccountNumber(senderaccountNumber);
		restTemplate.postForEntity("http://localhost:8090/transactions/withdraw", 
				transaction, null);
		transaction.setAccountNumber(receiveraccountNumber);
		restTemplate.postForEntity("http://localhost:8090/transactions/deposit", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "FundTransferForm";
						}
	
	@RequestMapping("/gettransactions")
	public ModelAndView gettransactions(@RequestParam("offset")int offset,@RequestParam("size")int size)
	{
		CurrentDataSet dataset = restTemplate.getForObject("http://localhost:8090/transactions/getall", CurrentDataSet.class); 
		int currentsize=size==0?3:size;
		int currentoffset = offset==0?1:offset;
		Link next= linkTo(methodOn(BankAppController.class).gettransactions(currentoffset+currentsize, currentsize)).withRel("next");
		Link prev= linkTo(methodOn(BankAppController.class).gettransactions(currentoffset-currentsize, currentsize)).withRel("prev");
		List<Transaction> currentDataSet=new ArrayList<Transaction>();
		for(int i=currentoffset-1;i<currentsize+currentoffset-1;i++)
		{
			List<Transaction> transactions = dataset.getTransaction();
			Transaction transaction=transactions.get(i);
			currentDataSet.add(transaction);
		}
		CurrentDataSet datasetList=new CurrentDataSet(currentDataSet,next,prev);
		return new ModelAndView("DepositForm","currentDataSet",datasetList);				
	}
}

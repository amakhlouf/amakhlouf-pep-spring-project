package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.GenericClientException;
import com.example.exception.UnauthorizedLoginException;
import com.example.exception.UsernameConflictException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
	private AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository){
		this.accountRepository = accountRepository;
	}

	/**
	 * register
	 * @param account
	 * @return
	 */
	public Account register(Account account){
		if(!isValidUsername(account.getUsername()))
			throw new GenericClientException();
		if(!isValidPassword(account.getPassword()))
			throw new GenericClientException();
		if(accountRepository.findByUsername(account.getUsername()).isPresent())
			throw new UsernameConflictException();

		return accountRepository.save(account);
	}

	/**
	 * login
	 * @param account
	 * @return
	 */
	public Account login(Account account){
		return accountRepository.findByUsername(account.getUsername())
			.map(check -> check.getPassword().equals(account.getPassword()) ? check : null)
			.orElseThrow(() -> new UnauthorizedLoginException());
	}

	/**
	 * isValidUsername
	 * @param username
	 * @return
	 */
	private boolean isValidUsername(String username) {
		return username.length() >= 1;
	}

	/**
	 * isValidPassword
	 * @param password
	 * @return
	 */
	private boolean isValidPassword(String password) {
		return password.length() >= 4;
	}
}

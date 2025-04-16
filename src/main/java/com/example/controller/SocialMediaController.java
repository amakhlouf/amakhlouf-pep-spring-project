package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
	AccountService accountService;
	MessageService messageService;

	public SocialMediaController(AccountService accountService, MessageService messageService) {
		this.accountService = accountService;
		this.messageService = messageService;
	}

	/**
	 * postRegister
	 * @param account
	 * @return
	 */
	@PostMapping("register")
	public Account postRegister(@RequestBody Account account) {
		return accountService.register(account);
	}

	/**
	 * postLogin
	 * @param account
	 * @return
	 */
	@PostMapping("login")
	public Account postLogin(@RequestBody Account account) {
		return accountService.login(account);
	}

	/**
	 * getAccountMessages
	 * @param id
	 * @return
	 */
	@GetMapping("accounts/{id}/messages")
	public List<Message> getAccountMessages(@PathVariable int id) {
		return messageService.listByAccountId(id);
	}

	/**
	 * getAllMessages
	 * @return
	 */
	@GetMapping("messages")
	public List<Message> getAllMessages() {
		return messageService.list();
	}

	/**
	 * postMessage
	 * @param message
	 * @return
	 */
	@PostMapping("messages")
	public Message postMessage(@RequestBody Message message) {
		return messageService.create(message);
	}

	/**
	 * getMessage
	 * @param id
	 * @return
	 */
	@GetMapping("messages/{id}")
	public Message getMessage(@PathVariable int id) {
		return messageService.getById(id);
	}

	/**
	 * patchMessage
	 * @param id
	 * @param message
	 * @return
	 */
	@PatchMapping("messages/{id}")
	public int patchMessage(@PathVariable int id, @RequestBody Message message) {
		return messageService.update(id, message);
	}

	/**
	 * deleteMessage
	 * @param id
	 * @return
	 */
	@DeleteMapping("messages/{id}")
	public ResponseEntity<Integer> deleteMessage(@PathVariable int id) {
		return ResponseEntity.status(200).body(messageService.delete(id).orElse(null));
	}
}

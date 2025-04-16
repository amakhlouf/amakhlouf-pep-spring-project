package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.GenericClientException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
	private MessageRepository messageRepository;
	private AccountRepository accountRepository;

	public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
		this.messageRepository = messageRepository;
		this.accountRepository = accountRepository;
	}

	/**
	 * list
	 * @return
	 */
	public List<Message> list(){
		return messageRepository.findAll();
	}

	/**
	 * listByAccountId
	 * @param accountId
	 * @return
	 */
	public List<Message> listByAccountId(int accountId){
		return messageRepository.findByPostedBy(accountId);
	}

	/**
	 * getById
	 * @param id
	 * @return
	 */
	public Message getById(int id){
		return messageRepository.findById(id).orElse(null);
	}

	/**
	 * create
	 * @param message
	 * @return
	 */
	public Message create(Message message){
		if(!isValidText(message.getMessageText()))
			throw new GenericClientException();

		if(!existsAccountId(message.getPostedBy()))
			throw new GenericClientException();

		return messageRepository.save(message);
	}

	/**
	 * update
	 * @param id
	 * @param message
	 * @return
	 */
	public int update(int id, Message message){
		String text = message.getMessageText();

		if(!isValidText(text))
			throw new GenericClientException();

		message = messageRepository.findById(id)
			.orElseThrow(() -> new GenericClientException());

		message.setMessageText(text);

		try {
			messageRepository.save(message);
		} catch (Exception e) {
			throw new GenericClientException();
		}

		return 1;
	}

	/**
	 * delete
	 * @param id
	 * @return
	 */
	public Optional<Integer> delete(int id){
		try {
			messageRepository.deleteById(id);
		} catch (Exception e) {
			return Optional.empty();
		}

		return Optional.of(1);
	}

	/**
	 * isValidText
	 * @param text
	 * @return
	 */
	private boolean isValidText(String text) {
		if(text.length() < 1)
			return false;

		if(text.length() > 255)
			return false;

		return true;
	}

	/**
	 * existsAccountId
	 * @param accountRepository
	 * @param accountId
	 * @return
	 */
	private boolean existsAccountId(int accountId) {
		return accountRepository.existsById(accountId);
	}
}

package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	/**
	 * findByPostedBy
	 * @param postedBy
	 * @return
	 */
	public List<Message> findByPostedBy(Integer postedBy);
}

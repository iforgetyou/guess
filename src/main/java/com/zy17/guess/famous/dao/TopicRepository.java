package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.Topic;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic, String> {
}
package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.EventMessageEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface EventMessageRepository extends PagingAndSortingRepository<EventMessageEntity, String> {

  Page<EventMessageEntity> findAllByMsgType(String msgType, Pageable pageable);

  long countAllByMsgType(String msgType);
}
package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.WeixinUser;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface WeixinUserRepository extends PagingAndSortingRepository<WeixinUser, String> {
}
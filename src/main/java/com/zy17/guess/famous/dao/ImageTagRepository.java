package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.ImageTag;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface ImageTagRepository extends PagingAndSortingRepository<ImageTag, String> {

}
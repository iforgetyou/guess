package com.zy17.guess.famous.dao;

import com.zy17.guess.famous.entity.Celebrity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "celebrity", path = "celebrity")
public interface CelebrityRepository extends PagingAndSortingRepository<Celebrity, String> {

}
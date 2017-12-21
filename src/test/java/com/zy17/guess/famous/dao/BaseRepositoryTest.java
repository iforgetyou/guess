package com.zy17.guess.famous.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 2017/2/22 zy17
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BaseRepositoryTest {

  @Autowired
  public TestEntityManager entityManager;
  @Autowired
  public EventMessageRepository repository;
  @Autowired
  public ImageTagRepository tagDao;
}
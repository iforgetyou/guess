package com.zy17.guess.famous.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.other.EventType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import weixin.popular.bean.message.EventMessage;

/**
 * 2017/2/22 yanzhang153
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EventMessageRepositoryTest {


  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private EventMessageRepository repository;

  @Test
  public void testExample() throws Exception {

    EventMessage msg = new EventMessage();
    msg.setFromUserName("1");
    msg.setMsgId("1");
    EventMessageEntity entity = new EventMessageEntity(msg);
    this.entityManager.persist(entity);
    EventMessageEntity save = this.repository.save(entity);
    assertThat(save.getMsgId()).isEqualTo("1");
  }

  @Test
  public void testPage() {
    EventMessage msg = new EventMessage();
    msg.setFromUserName("1");
    msg.setMsgId("1");
    msg.setMediaId("1");
    msg.setMsgType("image");
    EventMessageEntity entity = new EventMessageEntity(msg);
    this.entityManager.persist(entity);
    EventMessageEntity save = this.repository.save(entity);
    System.out.println(save);
    long count = repository.countAllByMsgType(EventType.IMAGE.getValue());
    // 随机从找一张用户传图
    Pageable pageable = new PageRequest(new Random().nextInt((int) count), 1);
    Page<EventMessageEntity> all = repository.findAllByMsgType("image", pageable);
    System.out.println(all);
    assertThat(save.getMsgId()).isEqualTo(all.getContent().get(0).getMediaId());
  }

}
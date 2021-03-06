package com.zy17.guess.famous.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.zy17.guess.famous.entity.EventMessageEntity;
import com.zy17.guess.famous.entity.ImageTag;
import com.zy17.guess.famous.other.MsgType;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;

/**
 * 2017/2/22 zy17
 */
@ActiveProfiles("dev")
public class EventMessageEntityRepositoryTest extends BaseRepositoryTest {

  @Test
  public void testPage() {
    weixin.popular.bean.message.EventMessage msg = new weixin.popular.bean.message.EventMessage();
    msg.setFromUserName("1");
    msg.setMsgId("1");
    msg.setMediaId("1");
    msg.setMsgType("image");
    EventMessageEntity entity = new EventMessageEntity(msg);
    this.entityManager.persist(entity);
    EventMessageEntity save = this.repository.save(entity);
    System.out.println(save);
    long count = repository.countAllByMsgType(MsgType.IMAGE.getValue());
    // 随机从找一张用户传图
    Pageable pageable = new PageRequest(new Random().nextInt((int) count), 1);
    Page<EventMessageEntity> all = repository.findAllByMsgType("image", pageable);
    System.out.println(all);
    assertThat(save.getMsgId()).isEqualTo(all.getContent().get(0).getMsg().getMsgId());
  }

  @Test
  public void eventSave() {

//    <ToUserName>gh_85872950c618</ToUserName>
//    <FromUserName>oTbr8wJYZIU7cgMyPAGqRzsg9XX4</FromUserName>
//    <CreateTime>1487947435</CreateTime>
//    <MsgType>event</MsgType>
//    <Event>subscribe</Event>
//    <EventKey></EventKey>

    EventMessageEntity entity = new EventMessageEntity();
    entity.setToUserName("gh_85872950c618");
    entity.setFromUserName("oTbr8wJYZIU7cgMyPAGqRzsg9XX4");
    entity.setCreateTime(1487947435);
    entity.setMsgType("event");
    entity.setEvent("subscribe");
    if (MsgType.isEvent(entity.getMsgType())) {
      // event 没有msgId, 同一秒有并发问题

      entity.setMsgId("e_" + entity.getCreateTime());
    }
    repository.save(entity);
  }

  @Test
  public void findImageByTag() {
    String tag = "搜索标签";
    ImageTag entity = new ImageTag();
    entity.setTag(tag);
    entity.setTagMsgId("123");
    entity.setImageMsgId("345");
    tagDao.save(entity);
    entity.setImageMsgId("333");
    tagDao.save(entity);
    long count = tagDao.countByTag(tag);
    System.out.println(count);
    int page = new Random().nextInt((int) count);
    System.out.println(page);
    Pageable pageable = new PageRequest(page, 1);
    Page<ImageTag> imageTags = tagDao.findAllByTag(tag, pageable);
    System.out.println(imageTags.getContent());
    assertThat(imageTags.getNumber()).isGreaterThan(0);
  }

}
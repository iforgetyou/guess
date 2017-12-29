package com.zy17.guess.famous.contorller;

import com.zy17.guess.famous.dao.CelebrityRepository;
import com.zy17.guess.famous.douban.bean.CelebrityResult;
import com.zy17.guess.famous.entity.Celebrity;
import com.zy17.guess.famous.service.DoubanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2017/3/1 zy17
 */

@RestController
@RequestMapping("/celebrity")
public class CelebrityController {
  @Autowired
  CelebrityRepository celebrityRepository;
  @Autowired
  DoubanService doubanService;

  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public Celebrity create(@RequestParam() String id) {
    Celebrity celebrity = new Celebrity();
    CelebrityResult result = doubanService.findByid(id);
    if (result != null) {
      celebrity.setId(result.getId());
      celebrity.setName(result.getName());
      celebrity.setNameEn(result.getName_en());
      celebrity.setAlt(result.getAlt());
      celebrity.setAvatarL(result.getAvatars().get(CelebrityResult.IMAGE_TYPE_LARGE));
      celebrity.setAvatarM(result.getAvatars().get(CelebrityResult.IMAGE_TYPE_MEDIUM));
      celebrity.setAvatarS(result.getAvatars().get(CelebrityResult.IMAGE_TYPE_SMALL));
      celebrity.setBirthday(result.getBirthday());
      celebrity.setBornPlace(result.getBorn_place());
      celebrity.setConstellation(result.getConstellation());
      celebrity.setSummary(result.getSummary());
      celebrity.setMobileUrl(result.getMobile_url());
      celebrity.setWebsite(result.getWebsite());
      celebrity.setGender(result.getGender());

      celebrityRepository.save(celebrity);
    }
    return celebrity;
  }
}

package com.zy17.guess.famous.contorller;

import com.zy17.guess.famous.dao.SubjectRepository;
import com.zy17.guess.famous.entity.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 2017/3/1 zy17
 */

@Controller
@RequestMapping("/subject")
public class SubjectController {
  @Autowired
  private SubjectRepository subjectRepository;
  @Value("${image.cloud.url}")
  private String imageCloudUrl;

  @RequestMapping("")
  public String greeting(
      @RequestParam() String id,
      Model model) {
    Subject subject = subjectRepository.findOne(id);
    if (subject.getCelebrityUrl() == null) {
      subject.setCelebrityUrl(imageCloudUrl + "/" + subject.getSubjectId());
    }
    model.addAttribute("subject", subject);
    return "image";
  }
}

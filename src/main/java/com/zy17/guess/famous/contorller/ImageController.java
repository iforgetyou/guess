package com.zy17.guess.famous.contorller;

import com.zy17.guess.famous.dao.SubjectRepository;
import com.zy17.guess.famous.entity.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 2017/3/1 zy17
 */

@Controller
@RequestMapping("/image")
public class ImageController {
  @Autowired
  SubjectRepository subjectRepository;

  @RequestMapping("")
  public String greeting(
      @RequestParam() String id,
      Model model) {
    Subject subject = subjectRepository.findOne(id);
    model.addAttribute("subject", subject);
    return "image";
  }
}

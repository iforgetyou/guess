package com.zy17.guess.famous.other;

import lombok.Getter;

/**
 * 2017/2/22 zy17
 */
@Getter
public enum MenuAnswerEnum {
  A("answer_A","1"),
  B("answer_B","2"),
  C("answer_C","3"),
  D("answer_D","4");

  private String value;
  private String answer;

  MenuAnswerEnum(String value,String answer) {
    this.value = value;
    this.answer = answer;
  }

  public static MenuAnswerEnum convertEnum(String msg) {
    for (MenuAnswerEnum cmdType : MenuAnswerEnum.values()) {
      if (cmdType.getValue().equals(msg)) {
        return cmdType;
      }
    }
    return null;
  }
}

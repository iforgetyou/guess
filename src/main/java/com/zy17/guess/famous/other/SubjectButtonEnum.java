package com.zy17.guess.famous.other;

import lombok.Getter;

/**
 * 2017/2/22 zy17
 */
@Getter
public enum SubjectButtonEnum {
  subject_1("subject_1","1");

  private String value;
  private String subjectId;

  SubjectButtonEnum(String value,String subjectId) {
    this.value = value;
    this.subjectId=subjectId;
  }

  public static SubjectButtonEnum convertEnum(String msg) {
    for (SubjectButtonEnum cmdType : SubjectButtonEnum.values()) {
      if (cmdType.getValue().equals(msg)) {
        return cmdType;
      }
    }
    return null;
  }
}

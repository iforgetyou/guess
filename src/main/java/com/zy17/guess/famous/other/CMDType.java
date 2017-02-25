package com.zy17.guess.famous.other;

import lombok.Getter;

/**
 * 2017/2/22 zy17
 */
@Getter
public enum CMDType {
  SEARCH("1"), ANSWER("2");

  private String value;

  CMDType(String value) {
    this.value = value;
  }

  public static boolean isCmd(String msg) {
    for (CMDType cmdType : CMDType.values()) {
      if (cmdType.getValue().equals(msg)) {
        return true;
      }
    }
    return false;
  }

  public static boolean notCmd(String msg) {
    return !isCmd(msg);
  }
}

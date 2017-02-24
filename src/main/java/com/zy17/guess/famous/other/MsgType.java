package com.zy17.guess.famous.other;

import lombok.Getter;

/**
 * 2017/2/22 zy17
 */
@Getter
public enum MsgType {
  EVENT("event"), SUBSCRIBE("subscribe"), TEXT("text"), IMAGE("image"), VOICE("voice"), SHORTVIDEO("shortvideo"), VIDEO("video");

  private String value;

  MsgType(String value) {
    this.value = value;
  }

  public static boolean isEvent(String msgType) {
    if (msgType.equals(MsgType.EVENT.getValue())) {
      return true;
    }
    return false;
  }
}

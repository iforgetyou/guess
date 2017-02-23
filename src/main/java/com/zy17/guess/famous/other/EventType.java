package com.zy17.guess.famous.other;

import lombok.Getter;

/**
 * 2017/2/22 iforgetyou
 */
@Getter
public enum EventType {
  EVENT("event"), SUBSCRIBE("subscribe"), TEXT("text"), IMAGE("image"), VOICE("voice"), SHORTVIDEO("shortvideo"), VIDEO("video");

  private String value;

  EventType(String value) {
    this.value = value;
  }


}

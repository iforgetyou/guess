package com.zy17.guess.famous.other;

import lombok.Getter;

/**
 * 2017/2/22 zy17
 */
@Getter
public enum EventType {
  SUBSCRIBE("subscribe"), SCAN("SCAN"), LOCATION("LOCATION"), CLICK("CLICK"), VIEW("VIEW");

  private String value;

  EventType(String value) {
    this.value = value;
  }

}

package com.zy17.guess.famous.service;

import com.alibaba.fastjson.JSON;
import com.zy17.guess.famous.SpringBootTestBase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import weixin.popular.api.MenuAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.Menu;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.bean.token.Token;

/**
 * Created by zy on 2017/12/25. Desc:
 */
public class MenuTest  {
  @Test
  public void getMenuTest(){
//    Token token = TokenAPI.token("wxd1634667029f343a", "01a0cab011cd7a937a2e1e24eb250fb1");
    String token="5_hTYYJ0z3WNilGz_e3c4B3ul9QK3QPfGl08HH_R31y3wlJT6JHnTMbmFgs5ZWKIFsmrc4I704GbzUO_ee5fxQLdUEorZpLvfDZnzh5XM5yj7YgyUD0oFN7Bgn62wwhdU7qp9iRyFmL4C8UWWlNGNcACATMT";
    Menu menu = MenuAPI.menuGet(token);

    System.out.println(JSON.toJSONString(menu));
    MenuButtons buttonsMenu = new MenuButtons();
    List<Button> buttons = new ArrayList<>();


    for (int i = 0; i < 3; i++) {
      Button button = new Button();
      button.setName("button");
      button.setType("click");
      buttons.add(button);
    }
    Button[] arr = new Button[buttons.size()];
    buttonsMenu.setButton(buttons.toArray(arr));
    System.out.println(JSON.toJSONString(buttonsMenu));
//    BaseResult baseResult = MenuAPI.menuCreate(token, buttonsMenu);
//    System.out.println(JSON.toJSONString(baseResult));
  }
}

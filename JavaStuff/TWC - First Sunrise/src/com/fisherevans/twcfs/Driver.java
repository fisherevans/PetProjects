package com.fisherevans.twcfs;

import com.fisherevans.twcfs.game.Game;
import org.newdawn.slick.AppGameContainer;

/**
 * Created by h13730 on 10/27/2015.
 */
public class Driver {

  public static void main(String[] args) {
    try {
      AppGameContainer container = new AppGameContainer(Game.getInstance());
      //container.setIcon("res/img/icon.png");
      container.setDisplayMode(Constants.BASE_WIDTH*Constants.SCALE, Constants.BASE_HEIGHT*Constants.SCALE, false);
      container.setUpdateOnlyWhenVisible(false);
      container.setAlwaysRender(true);
      container.setShowFPS(false);
      container.setMouseGrabbed(false);
      container.setVSync(true);

      container.start();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}

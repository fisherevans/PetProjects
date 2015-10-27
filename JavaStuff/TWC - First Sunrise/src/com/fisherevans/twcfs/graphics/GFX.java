package com.fisherevans.twcfs.graphics;

import com.fisherevans.twcfs.game.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by h13730 on 10/27/2015.
 */
public class GFX {
  public static void fill(Graphics gfx, Color color) {
    gfx.fillRect(0, 0, Game.getInstance().getContainer().getWidth(), Game.getInstance().getContainer().getHeight());
  }
}

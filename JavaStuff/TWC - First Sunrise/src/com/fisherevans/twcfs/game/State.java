package com.fisherevans.twcfs.game;

import com.fisherevans.twcfs.input.Key;
import com.fisherevans.twcfs.input.KeyConsumer;
import org.newdawn.slick.Graphics;

/**
 * Created by h13730 on 10/27/2015.
 */
public abstract class State implements KeyConsumer {
  public void enter() {

  }

  public abstract void update(float delta);

  public abstract void render(Graphics gfx);

  protected boolean getKeyState(Key key) {
    return Game.getInstance().getKeyboardManager().getKeyState(key);
  }
}

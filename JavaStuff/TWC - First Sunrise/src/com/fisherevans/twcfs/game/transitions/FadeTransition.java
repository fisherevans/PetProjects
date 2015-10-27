package com.fisherevans.twcfs.game.transitions;

import com.fisherevans.twcfs.game.Game;
import com.fisherevans.twcfs.game.State;
import com.fisherevans.twcfs.game.Transition;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by h13730 on 10/27/2015.
 */
public class FadeTransition extends Transition {
  public FadeTransition(Builder builder) {
    super(builder);
  }

  @Override
  public void render(Graphics gfx) {
    State state = getPercentDone() < 0.5 ? getLeaving() : getEntering();
    state.render(gfx);
    float opacity = 1f - (2f*Math.abs(getPercentDone() - 0.5f));
    gfx.setColor(new Color(0f, 0f, 0f, opacity));
    gfx.fillRect(0, 0, Game.getInstance().getContainer().getWidth(), Game.getInstance().getContainer().getHeight());
  }
}

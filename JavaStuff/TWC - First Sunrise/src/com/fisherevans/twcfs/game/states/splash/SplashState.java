package com.fisherevans.twcfs.game.states.splash;

import com.fisherevans.twcfs.game.Game;
import com.fisherevans.twcfs.game.State;
import com.fisherevans.twcfs.game.Transition;
import com.fisherevans.twcfs.game.states.adventure.AdventureState;
import com.fisherevans.twcfs.game.states.dummy.DummyState;
import com.fisherevans.twcfs.game.transitions.FadeTransition;
import com.fisherevans.twcfs.input.Key;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by h13730 on 10/27/2015.
 */
public class SplashState extends State {
  @Override
  public void update(float delta) {

  }

  @Override
  public void render(Graphics gfx) {
    gfx.setColor(Color.white);
    gfx.drawString("Splash", 20, 20);
  }

  @Override
  public void keyEvent(Key key, boolean state) {
    if(key == Key.Select && state == true) {
      Transition.Builder builder = new Transition.Builder(this, new AdventureState(), 1f)
          .setInterpolation(Transition.Interpolation.Linear);
      Game.getInstance().setCurrentState(new FadeTransition(builder));
    }
    if(key == Key.Exit && state == true) {
      System.exit(0);
    }
  }
}

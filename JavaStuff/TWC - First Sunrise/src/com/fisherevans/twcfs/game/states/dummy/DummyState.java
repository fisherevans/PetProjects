package com.fisherevans.twcfs.game.states.dummy;

import com.fisherevans.twcfs.game.Game;
import com.fisherevans.twcfs.game.State;
import com.fisherevans.twcfs.game.Transition;
import com.fisherevans.twcfs.game.transitions.FadeTransition;
import com.fisherevans.twcfs.input.Key;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by h13730 on 10/27/2015.
 */
public class DummyState extends State {
  private State _nextState;

  public DummyState(State nextState) {
    _nextState = nextState;
  }

  @Override
  public void update(float delta) {

  }

  @Override
  public void render(Graphics gfx) {
    gfx.setColor(Color.white);
    gfx.drawString("Dummy!", 20, 60);
  }

  @Override
  public void keyEvent(Key key, boolean state) {
    if(key == Key.Select && state == true) {
      Transition.Builder builder = new Transition.Builder(this, _nextState, 1f)
          .setInterpolation(Transition.Interpolation.Cosine);
      Game.getInstance().setCurrentState(new FadeTransition(builder));
    }
  }
}

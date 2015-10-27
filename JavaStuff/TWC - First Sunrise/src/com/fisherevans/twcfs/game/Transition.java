package com.fisherevans.twcfs.game;

import com.fisherevans.twcfs.input.Key;
import org.lwjgl.Sys;

/**
 * Created by h13730 on 10/27/2015.
 */
public abstract class Transition extends State {
  private State _leaving, _entering;

  private boolean _updateLeaving, _updateEntering;

  private boolean _inputLeaving, _inputEntering;

  private Interpolation _interpolationMethod;

  private float _duration, _elapsed;

  public Transition(Builder builder) {
    _leaving = builder.leaving;
    _entering = builder.entering;
    _updateLeaving = builder.updateLeaving;
    _updateEntering = builder.updateEntering;
    _inputLeaving = builder.inputLeaving;
    _inputEntering = builder.inputEntering;
    _interpolationMethod = builder.interpolationMethod;
    _duration = builder.duration;
    _elapsed = builder.initialElapsed;
  }

  @Override
  public void update(float delta) {
    _elapsed += delta;
    if(_elapsed >= _duration) {
      Game.getInstance().setCurrentState(_entering);
    } else {
      if(_leaving != null && _updateLeaving)
        _leaving.update(delta);
      if(_entering != null && _updateEntering)
        _entering.update(delta);
    }
  }

  @Override
  public void keyEvent(Key key, boolean state) {
    if(_leaving != null && _inputLeaving)
      _leaving.keyEvent(key, state);
    if(_entering != null && _inputEntering)
      _entering.keyEvent(key, state);
  }

  public float getPercentDone() {
    if(_duration == 0)
      return 1f;
    else {
      float linear = _elapsed / _duration;
      switch(_interpolationMethod) {
        case Cosine:
          float cosine = (float) ((1.0 - Math.cos(linear*Math.PI))/2.0);
          return cosine;
        case Linear: // default
        default:
          return linear;

      }

    }
  }

  public State getLeaving() {
    return _leaving;
  }

  public State getEntering() {
    return _entering;
  }

  public static enum Interpolation { Linear, Cosine }

  public static class Builder {
    private State leaving, entering;

    private boolean updateLeaving = false, updateEntering = false;

    private boolean inputLeaving = false, inputEntering = false;

    private Interpolation interpolationMethod = Interpolation.Linear;

    private float duration = 0f, initialElapsed = 0f;

    public Builder(State leaving, State entering, float duration) {
      this.leaving = leaving;
      this.entering = entering;
      this.duration = duration;
    }

    public Builder setUpdate(boolean leaving, boolean entering) {
      updateLeaving = leaving;
      updateEntering = entering;
      return this;
    }

    public Builder setInput(boolean leaving, boolean entering) {
      inputLeaving = leaving;
      inputEntering = entering;
      return this;
    }

    public Builder setInterpolation(Interpolation interpolationMethod) {
      this.interpolationMethod = interpolationMethod;
      return this;
    }

    public Builder setInitialElapsed(float initialElapsed) {
      this.initialElapsed = initialElapsed;
      return this;
    }
  }
}

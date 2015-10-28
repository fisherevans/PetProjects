package com.fisherevans.twcfs.game.states.adventure.physics;

/**
 * Created by h13730 on 10/28/2015.
 */
public class Vector {
  private float _x, _y;

  public Vector() {
    this(0, 0);
  }

  public Vector(float x, float y) {
    _x = x;
    _y = y;
  }

  public float getX() {
    return _x;
  }

  public void setX(float x) {
    _x = x;
  }

  public float getY() {
    return _y;
  }

  public void setY(float y) {
    _y = y;
  }
}

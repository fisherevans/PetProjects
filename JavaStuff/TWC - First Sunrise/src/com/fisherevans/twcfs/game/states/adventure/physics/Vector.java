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

  /**
   * adds a given vector to this one
   * @param vector the vector to add
   * @return this vector after the addition
   */
  public Vector add(Vector vector) {
    _x += vector.getX();
    _y += vector.getY();
    return this;
  }

  public Vector add(Vector vector, float scale) {
    _x += vector.getX()*scale;
    _y += vector.getY()*scale;
    return this;
  }

  /**
   * subtract a given vector to this one
   * @param vector the vector to subtract
   * @return this vector after the subtraction
   */
  public Vector subtract(Vector vector) {
    _x -= vector.getX();
    _y -= vector.getY();
    return this;
  }

  /**
   * multiplies a given vector to this one
   * @param vector the vector to multiply by
   * @return this vector after the multiplication
   */
  public Vector multiply(Vector vector) {
    _x *= vector.getX();
    _y *= vector.getY();
    return this;
  }

  /**
   * scales thi vector by a given magnitude
   * @param magnitude the amount to scale by
   * @return this vector after the scaling
   */
  public Vector scale(float magnitude) {
    _x *= magnitude;
    _y *= magnitude;
    return this;
  }

  /**
   * @return the length of this vecotr
   */
  public float getLength() {
    return (float) Math.sqrt(getSquaredLength());
  }

  /**
   * @return the squared length of this vecotr
   */
  public float getSquaredLength() {
    return _x*_x + _y*_y;
  }

  /**
   * returns the result of dot multiplying this vector by another
   * @param vector the vector to dot multiply with
   * @return the dot multiply result
   */
  public float dot(Vector vector) {
    return _x*vector.getX() + _y*vector.getY();
  }

  /**
   * normalizes this vector
   * @return this vector after normalization
   */
  public Vector normalize() {
    float startLength = getLength();
    _x /= startLength;
    _y /= startLength;
    return this;
  }

  /**
   * gets the angle produced by this vector
   * @return the angle
   */
  public float getAngle() {
    return (float) Math.atan2(_y, _x);
  }

  /**
   * creates an identical copy of this vector in a new instance
   * @return the copy
   */
  public Vector getCopy() {
    return new Vector(_x, _y);
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

  public Vector clamp(Vector low, Vector high) {
    if(getX() < low.getX()) setX(low.getX());
    else if(getX() > high.getX()) setX(high.getX());
    if(getY() < low.getY()) setY(low.getY());
    else if(getY() > high.getY()) setY(high.getY());
    return this;
  }

  /**
   * creats a vector from an angle and magnitude
   * @param angle the angle of the vector
   * @param length the length of the vector
   * @return the vector based on the given angle and length
   */
  public static Vector fromAngle(float angle, float length) {
    float x = (float) Math.cos(angle)*length;
    float y = (float) Math.sin(angle)*length;
    return new Vector(x, y);
  }

  @Override
  public String toString() {
    return String.format("(%.2f, %.2f)", _x, _y);
  }
}

package com.fisherevans.twcfs.game.states.adventure.physics;

/**
 * Created by h13730 on 10/28/2015.
 */
public class Box {
  private Vector _size, _position, _velocity, _acceleration;

  public Box(Builder builder) {
    this(new Vector(builder.width, builder.height),
        new Vector(builder.x, builder.y),
        new Vector(builder.vx, builder.vy),
        new Vector(builder.ax, builder.ay));
  }

  public Box(Vector size, Vector position, Vector velocity, Vector acceleration) {
    _size = size;
    _position = position;
    _velocity = velocity;
    _acceleration = acceleration;
  }

  public float getX() {
    return _position.getX();
  }

  public void setX(float x) {
    _position.setX(x);
  }

  public float getY() {
    return _position.getY();
  }

  public void setY(float y) {
    _position.setY(y);
  }

  public float getWidth() {
    return _size.getX();
  }

  public void setWidth(float width) {
    _size.setX(width);
  }

  public float getHeight() {
    return _size.getY();
  }

  public void setHeight(float height) {
    _size.setY(height);
  }

  public Vector getSize() {
    return _size;
  }

  public void setSize(Vector size) {
    _size = size;
  }

  public Vector getPosition() {
    return _position;
  }

  public void setPosition(Vector position) {
    _position = position;
  }

  public Vector getVelocity() {
    return _velocity;
  }

  public void setVelocity(Vector velocity) {
    _velocity = velocity;
  }

  public Vector getAcceleration() {
    return _acceleration;
  }

  public void setAcceleration(Vector acceleration) {
    _acceleration = acceleration;
  }

  public String toString() {
    return String.format("[X:%4.2f, Y:%4.2f, W:%4.2f, H:%4.2f]",
        getX(), getY(), getWidth(), getHeight());
  }

  public static class Builder {
    private float width = 0, height = 0;
    private float x = 0, y = 0;
    private float vx = 0, vy = 0;
    private float ax = 0, ay = 0;

    public Builder() {}

    public Builder size(float width, float height) {
      this.width = width;
      this.height = height;
      return this;
    }

    public Builder position(float x, float y) {
      this.x = x;
      this.y = y;
      return this;
    }

    public Builder velocity(float vx, float vy) {
      this.vx = vx;
      this.vy = vy;
      return this;
    }

    public Builder acceleration(float ax, float ay) {
      this.ax = ax;
      this.ay = ay;
      return this;
    }

    public Box build() {
      return new Box(this);
    }
  }
}

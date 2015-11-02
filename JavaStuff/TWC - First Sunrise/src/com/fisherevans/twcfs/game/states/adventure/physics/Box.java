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

  public float getCenterX() {
    return getX() + getWidth()/2f;
  }

  public float getCenterY() {
    return getY() + getHeight()/2f;
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

  public boolean canJoin(Box other) {
    return (
              getX() == other.getX() &&
              getWidth() == other.getWidth() &&
              getY() + getHeight() == other.getY()
           ) || (
              getY() == other.getY() &&
              getHeight() == other.getHeight() &&
              getX() + getWidth() == other.getX()
          );
  }

  public void add(Box other) {
    float x1 = Math.min(getX(), other.getX());
    float y1 = Math.min(getY(), other.getY());
    float x2 = Math.max(getX() + getWidth(), other.getX() + other.getWidth());
    float y2 = Math.max(getY() + getHeight(), other.getY() + other.getHeight());
    _position = new Vector(x1, y1);
    _size = new Vector(x2-x1, y2-y1);
  }

  public boolean intersectsX(Box other) {
    return getX() < other.getX() + other.getWidth() && other.getX() < getX() + getWidth();
  }

  public boolean intersectsY(Box other) {
    return getY() < other.getY() + other.getHeight() && other.getY() < getY() + getHeight();
  }

  public boolean intersects(Box other) {
    return intersectsX(other) && intersectsY(other);
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

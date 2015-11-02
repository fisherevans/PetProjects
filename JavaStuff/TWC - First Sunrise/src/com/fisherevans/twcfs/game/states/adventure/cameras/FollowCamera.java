package com.fisherevans.twcfs.game.states.adventure.cameras;

import com.fisherevans.twcfs.game.states.adventure.physics.Box;
import com.fisherevans.twcfs.game.states.adventure.physics.Vector;

/**
 * Created by immortal on 10/30/2015.
 */
public class FollowCamera implements Camera {
  private Box _box;
  private float _speed;
  private Vector _position;

  public FollowCamera(Box box, float speed) {
    _box = box;
    _speed = speed;
    _position = new Vector(_box.getCenterX(), _box.getCenterY());
  }

  @Override
  public void update(float delta) {
    Vector deltaPosition = new Vector(_box.getCenterX() - _position.getX(), _box.getCenterY() - _position.getY());
    deltaPosition.scale(_speed*delta);
    _position.add(deltaPosition);
  }

  @Override
  public float getCameraX() {
    return _position.getX();
  }

  @Override
  public float getCameraY() {
    return _position.getY();
  }
}

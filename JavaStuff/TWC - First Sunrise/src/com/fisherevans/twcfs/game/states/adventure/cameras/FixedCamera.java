package com.fisherevans.twcfs.game.states.adventure.cameras;

import com.fisherevans.twcfs.game.states.adventure.physics.Box;

/**
 * Created by immortal on 10/30/2015.
 */
public class FixedCamera implements Camera {
  private Box _box;

  public FixedCamera(Box box) {
    _box = box;
  }

  @Override
  public void update(float delta) {

  }

  @Override
  public float getCameraX() {
    return _box.getCenterX();
  }

  @Override
  public float getCameraY() {
    return _box.getCenterY();
  }
}

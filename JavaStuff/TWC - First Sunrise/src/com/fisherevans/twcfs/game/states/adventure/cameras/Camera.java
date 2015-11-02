package com.fisherevans.twcfs.game.states.adventure.cameras;

/**
 * Created by immortal on 10/30/2015.
 */
public interface Camera {
  public void update(float delta);
  public float getCameraX();
  public float getCameraY();
}

package com.fisherevans.twcfs.game.states.adventure.physics;

import java.util.*;

/**
 * Created by h13730 on 10/28/2015.
 */
public class World {
  private BoxCollection _boxes;
  private Vector _gravity;

  public World() {
    _boxes = new BoxCollection();
    _gravity = new Vector();
  }

  public void add(Box box) {
    _boxes.add(box);
  }
}

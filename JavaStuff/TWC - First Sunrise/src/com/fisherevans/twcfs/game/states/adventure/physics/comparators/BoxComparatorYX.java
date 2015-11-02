package com.fisherevans.twcfs.game.states.adventure.physics.comparators;

import com.fisherevans.twcfs.game.states.adventure.physics.Box;

import java.util.Comparator;

/**
 * Created by immortal on 10/30/2015.
 */
public class BoxComparatorYX implements Comparator<Box> {
  @Override
  public int compare(Box r1, Box r2) {
    if (r1.getY() < r2.getY()) return -1;
    else if (r1.getY() > r2.getY()) return 1;
    else if (r1.getX() < r2.getX()) return -1;
    else if (r1.getX() > r2.getX()) return 1;
    else return 0;
  }
}

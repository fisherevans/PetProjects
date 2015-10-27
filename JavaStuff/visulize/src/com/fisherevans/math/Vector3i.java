package com.fisherevans.math;

/**
 * Created by h13730 on 10/26/2015.
 */
public class Vector3i {
  public final int x, y, z;

  public static final Vector3i ZERO = new Vector3i(0, 0, 0);

  public Vector3i(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}

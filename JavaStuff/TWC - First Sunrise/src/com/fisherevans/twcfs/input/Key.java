package com.fisherevans.twcfs.input;

import static org.newdawn.slick.Input.*;

/**
 * Created by h13730 on 10/27/2015.
 */
public enum Key {
  Up(KEY_W, KEY_UP),
  Down(KEY_S, KEY_DOWN),
  Left(KEY_A, KEY_LEFT),
  Right(KEY_D, KEY_RIGHT),
  Select(KEY_SPACE, KEY_ENTER),
  Back(KEY_LCONTROL, KEY_RCONTROL),
  Exit(KEY_ESCAPE);

  public final Integer[] keyCodes;

  Key(Integer ... keyCodes) {
    this.keyCodes = keyCodes;
  }
}

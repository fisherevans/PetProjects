package com.fisherevans.twcfs.input;

import com.fisherevans.twcfs.game.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 10/27/2015.
 */
public class KeyboardManager implements KeyListener {
  private Map<Integer, Key> _keyMap;
  private Map<Key, Boolean> _keyStates;

  public KeyboardManager() {
    _keyStates = new HashMap<>();
    _keyMap = new HashMap<>();
    for(Key key:Key.values()) {
      _keyStates.put(key, false);
      for(Integer keyCode:key.keyCodes) {
        _keyMap.put(keyCode, key);
      }
    }
  }

  @Override
  public void keyPressed(int keyCode, char c) {
    Key key = _keyMap.get(keyCode);
    if(key != null)
      updateKey(key, true);
  }

  @Override
  public void keyReleased(int keyCode, char c) {
    Key key = _keyMap.get(keyCode);
    if(key != null)
      updateKey(key, false);
  }

  private void updateKey(Key key, boolean state) {
    _keyStates.put(key, state);
    if(Game.getInstance().getCurrentState() != null)
      Game.getInstance().getCurrentState().keyEvent(key, state);
  }

  public boolean getKeyState(Key key) {
    return _keyStates.get(key);
  }

  @Override
  public void setInput(Input input) { }

  @Override
  public boolean isAcceptingInput() { return true; }

  @Override
  public void inputEnded() { }

  @Override
  public void inputStarted() { }
}

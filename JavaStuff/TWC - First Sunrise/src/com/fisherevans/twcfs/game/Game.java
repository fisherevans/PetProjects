package com.fisherevans.twcfs.game;

import com.fisherevans.twcfs.Constants;
import com.fisherevans.twcfs.game.states.adventure.AdventureState;
import com.fisherevans.twcfs.game.states.splash.SplashState;
import com.fisherevans.twcfs.input.KeyboardManager;
import org.newdawn.slick.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 10/27/2015.
 */
public class Game extends BasicGame implements KeyListener {
  private static Game instance;

  private GameContainer _container;
  private KeyboardManager _keyboardManager;

  private State _currentState = null;

  private Game() {
    super(Constants.TITLE);
  }

  @Override
  public void init(GameContainer gameContainer) throws SlickException {
    _container = gameContainer;
    _keyboardManager = new KeyboardManager();
    gameContainer.getInput().addKeyListener(_keyboardManager);
    //_currentState = new SplashState();
    _currentState = new AdventureState();
  }

  @Override
  public void update(GameContainer gameContainer, int deltaMS) throws SlickException {
    if(_currentState == null)
      return;
    float delta = ((float)deltaMS)/1000f;
    _currentState.update(delta);
  }

  @Override
  public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
    if(_currentState == null)
      return;
    _currentState.render(graphics);
  }

  public State getCurrentState() {
    return _currentState;
  }

  public void setCurrentState(State currentState) {
    _currentState = currentState;
    if(_currentState != null)
      _currentState.enter();
  }

  public GameContainer getContainer() {
    return _container;
  }

  public KeyboardManager getKeyboardManager() {
    return _keyboardManager;
  }

  public static Game getInstance() {
    if(instance == null)
      instance = new Game();
    return instance;
  }
}

package com.fisherevans.twcfs.game.states.adventure;

import com.fisherevans.twcfs.Constants;
import com.fisherevans.twcfs.game.Game;
import com.fisherevans.twcfs.game.State;
import com.fisherevans.twcfs.game.states.adventure.cameras.Camera;
import com.fisherevans.twcfs.game.states.adventure.cameras.FollowCamera;
import com.fisherevans.twcfs.game.states.adventure.physics.Box;
import com.fisherevans.twcfs.game.states.adventure.physics.Vector;
import com.fisherevans.twcfs.game.states.adventure.physics.World;
import com.fisherevans.twcfs.game.states.adventure.physics.comparators.BoxComparatorXY;
import com.fisherevans.twcfs.game.states.adventure.physics.comparators.BoxComparatorYX;
import com.fisherevans.twcfs.input.Key;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by h13730 on 10/28/2015.
 */
public class AdventureState extends State {
  public static final int MAP_TILE_SIZE = 4;

  private TiledMap _map;
  private List<Box> _staticBoxes;
  private List<Box> _movingBoxes;
  private World _world;
  private Box _player;
  private boolean _canJump = true;

  private Camera _camera;

  private float _dSpeed = 75, _renderDX = 0, _renderDY = 0;

  private Vector _gravity = new Vector(0, 10);
  private Vector _terminalVelocityHigh = new Vector(500, 500);
  private Vector _terminalVelocityLow = new Vector(500, 500);

  public AdventureState() {
    _world = new World();
    loadMap("res/maps/test/test.tmx");
    createPlayer();
  }

  private void loadMap(String mapLocation) {
    try {
      _map = new TiledMap(mapLocation);
    } catch (SlickException e) {
      e.printStackTrace();
      System.exit(1);
    }
    _staticBoxes = new ArrayList<>();
    int staticLayer = _map.getLayerIndex("static"), tileId;
    for(int y = 0;y < _map.getHeight();y++)
      for(int x = 0;x < _map.getWidth();x++)
        if(_map.getTileId(x, y, staticLayer) > 0)
          _staticBoxes.add(new Box.Builder().position(x*MAP_TILE_SIZE, y*MAP_TILE_SIZE).size(MAP_TILE_SIZE, MAP_TILE_SIZE).build());
    if(_staticBoxes.size() != 0) {
      joinBoxes(new BoxComparatorYX());
      joinBoxes(new BoxComparatorXY());
    }
  }

  private void joinBoxes(Comparator<Box> comparator) {
    Box last = _staticBoxes.get(0), current;
    Collections.sort(_staticBoxes, comparator);
    for(int id = 1;id < _staticBoxes.size();) {
      current = _staticBoxes.get(id);
      if(last.canJoin(current)) {
        last.add(current);
        _staticBoxes.remove(id);
      } else {
        last = current;
        id++;
      }
    }
  }

  private void createPlayer() {
    _movingBoxes = new ArrayList<>();
    _player = new Box.Builder()
        .position(420, 270)
        .size(28, 60)
        .build();
    _camera = new FollowCamera(_player, 2f);
    //_camera = new FixedCamera(_player);
    _movingBoxes.add(_player);
  }

  @Override
  public void update(float delta) {
    //_world.step(delta);

    if(getKeyState(Key.Left))
      _player.getPosition().add(new Vector(-_dSpeed*delta, 0));
    if(getKeyState(Key.Right))
      _player.getPosition().add(new Vector(_dSpeed*delta, 0));

    if(getKeyState(Key.Up))
      _player.getPosition().add(new Vector(0, -_dSpeed*delta));
    if(getKeyState(Key.Down))
      _player.getPosition().add(new Vector(0, _dSpeed*delta));

/*    float accel = _player.getFloor() == Side.South ? 10000 : 2000f;
    if (getKeyState(Key.Right) && !getKeyState(Key.Left))
      _player.getAcceleration().setX(_player.getVelocity().getX() < 300 ? accel : -accel);
    else if (getKeyState(Key.Left) && !getKeyState(Key.Right))
      _player.getAcceleration().setX(_player.getVelocity().getX() > -300 ? -accel : accel);
    else {
      _player.getAcceleration().setX(0);
      _player.getVelocity().setX(_player.getVelocity().getX() - accel*delta*_player.getVelocity().getX()/240f);
    }*/
    moveBoxes(delta);
    _camera.update(delta);
  }

  private void moveBoxes(float delta) {
    Vector oldPosition;
    for(Box movingBox:_movingBoxes) {
      movingBox.getVelocity().add(movingBox.getAcceleration(), delta).add(_gravity, delta);
      movingBox.getVelocity().clamp(_terminalVelocityLow, _terminalVelocityHigh);
      oldPosition = movingBox.getPosition().getCopy();
      movingBox.getPosition().add(movingBox.getVelocity(), delta);
      for(Box staticBox:_staticBoxes) {
        if(movingBox.intersects(staticBox)) {
          movingBox.getPosition().add(new Vector(0, -10));
        }
      }
    }
  }

  @Override
  public void render(Graphics gfx) {
    calculateRenderDelta();
    for(Box box: _staticBoxes) {
      drawRect(gfx, box, Color.gray, Color.white);
    }
    drawRect(gfx, _player, Color.darkGray, Color.lightGray);
  }

  private void calculateRenderDelta() {
    _renderDX = Game.getInstance().getContainer().getWidth()/2f - Constants.SCALE*_camera.getCameraX();
    _renderDY = Game.getInstance().getContainer().getHeight()/2f - Constants.SCALE*_camera.getCameraY();
  }

  private void drawRect(Graphics gfx, Box box, Color fill, Color line) {
    gfx.setColor(fill);
    gfx.fillRect(Constants.SCALE*box.getX() + _renderDX,
        Constants.SCALE*box.getY() + _renderDY,
        Constants.SCALE*box.getWidth(),
        Constants.SCALE*box.getHeight());
    gfx.setColor(line);
    gfx.drawRect(Constants.SCALE*box.getX() + _renderDX,
        Constants.SCALE*box.getY() + _renderDY,
        Constants.SCALE*box.getWidth(),
        Constants.SCALE*box.getHeight());
  }

  @Override
  public void keyEvent(Key key, boolean state) {
    if(key == Key.Up && state == false) {
      _canJump = true;
    }
    if(key == Key.Exit && state == true) {
      System.exit(0);
    }
  }
}

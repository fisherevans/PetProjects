package com.fisherevans.twcfs.game.states.adventure;

import com.fisherevans.twcfs.game.Game;
import com.fisherevans.twcfs.game.State;
import com.fisherevans.twcfs.game.states.adventure.physics.Box;
import com.fisherevans.twcfs.game.states.adventure.physics.World;
import com.fisherevans.twcfs.input.Key;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
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
  private List<Box> _walls;
  private World _world;
  private Box _player;
  private boolean _canJump = true;

  private float _dSpeed = 200, _dx = 0, _dy = 0;

  public AdventureState() {
    _world = new World();
    loadMap("res/maps/test/test.tmx");
    _player = new Box.Builder()
        .position(20, 400)
        .size(28, 60)
        .build();
    _world.add(_player);
    calculateRenderDelta();
  }

  private void loadMap(String mapLocation) {
    try {
      _map = new TiledMap(mapLocation);
    } catch (SlickException e) {
      e.printStackTrace();
      System.exit(1);
    }
    _walls = new ArrayList<>();
    int collisionLayer = _map.getLayerIndex("collision"), tileId;
    for(int y = 0;y < _map.getHeight();y++) {
      int rectangleY = _map.getHeight()-(y);
      int start = 0;
      boolean cont = false;
      for(int x = 0;x <= _map.getWidth();x++) {
        tileId = x == _map.getWidth() ? 0 : _map.getTileId(x, y, collisionLayer);
        if(tileId == 0 && cont) {
          cont = false;
          _walls.add(new Box.Builder()
            .position(start * MAP_TILE_SIZE, rectangleY * MAP_TILE_SIZE)
            .size((x - start) * MAP_TILE_SIZE, MAP_TILE_SIZE)
            .build());
        } else if(tileId != 0 && !cont) {
          start = x;
          cont = true;
        }
      }
    }
    Collections.sort(_walls, new Comparator<Box>() {
      @Override
      public int compare(Box r1, Box r2) {
        if (r1.getX() < r2.getX()) return -1;
        else if (r1.getX() > r2.getX()) return 1;
        else if (r1.getY() < r2.getY()) return -1;
        else if (r1.getY() > r2.getY()) return 1;
        else return 0;
      }
    });
    for(Box box:_walls) {
      System.out.println(box);
    }
    System.out.println("Walls before: " + _walls.size());
    Box last = _walls.size() > 0 ? _walls.get(0) : null, current = null;
    for(int id = 1;id < _walls.size();) {
      current = _walls.get(id);
      if(last.getX() == current.getX()
          && last.getWidth() == current.getWidth()
          && last.getY() + last.getHeight() == current.getY()) {
        last.setHeight(last.getHeight() + current.getHeight());
        _walls.remove(id);
      } else {
        last = current;
        id++;
      }
    }
    System.out.println("Walls after: " + _walls.size());
    for(Box box:_walls) {
      System.out.println(box);
      _world.add(box);
    }
  }

  private void calculateRenderDelta() {
    _dx = _player.getX() + _player.getWidth()/2f - Game.getInstance().getContainer().getWidth()/2;
    _dy = _player.getY() + _player.getHeight()/2f - Game.getInstance().getContainer().getHeight()*1.5f;
  }

  @Override
  public void update(float delta) {
    //_world.step(delta);

/*    if(getKeyState(Key.Left))
      _dx += _dSpeed*delta;
    if(getKeyState(Key.Right))
      _dx -= _dSpeed*delta;

    if(getKeyState(Key.Up))
      _dy += _dSpeed*delta;
    if(getKeyState(Key.Down))
      _dy -= _dSpeed*delta;*/

/*    if(_canJump) {
      if (getKeyState(Key.Up) && _player.getFloor() == Side.South) {
        _player.getVelocity().setY(300);
        _canJump = false;
      }*//* else if(getKeyState(Key.Up) && _player.getWall() == Side.East) { // wall jumps
        _player.setVelocity(new Vector(-300f, 300f));
        _canJump = false;
      } else if(getKeyState(Key.Up) && _player.getWall() == Side.West) {
        _player.setVelocity(new Vector(300f, 300f));
        _canJump = false;
      }*//*
    }*/

/*    float accel = _player.getFloor() == Side.South ? 10000 : 2000f;
    if (getKeyState(Key.Right) && !getKeyState(Key.Left))
      _player.getAcceleration().setX(_player.getVelocity().getX() < 300 ? accel : -accel);
    else if (getKeyState(Key.Left) && !getKeyState(Key.Right))
      _player.getAcceleration().setX(_player.getVelocity().getX() > -300 ? -accel : accel);
    else {
      _player.getAcceleration().setX(0);
      _player.getVelocity().setX(_player.getVelocity().getX() - accel*delta*_player.getVelocity().getX()/240f);
    }*/

    calculateRenderDelta();
  }

  @Override
  public void render(Graphics gfx) {
    for(Box box:_walls) {
      drawRect(gfx, box, Color.gray, Color.white);
    }
    drawRect(gfx, _player, Color.darkGray, Color.lightGray);
  }

  private void drawRect(Graphics gfx, Box box, Color fill, Color line) {
    gfx.setColor(fill);
    gfx.fillRect(box.getX() - _dx, _map.getHeight()*MAP_TILE_SIZE - box.getY() + _dy, box.getWidth(), box.getHeight());
    gfx.setColor(line);
    gfx.drawRect(box.getX() - _dx, _map.getHeight()*MAP_TILE_SIZE - box.getY() + _dy, box.getWidth(), box.getHeight());
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

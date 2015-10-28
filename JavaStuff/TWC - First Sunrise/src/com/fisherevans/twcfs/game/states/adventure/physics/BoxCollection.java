package com.fisherevans.twcfs.game.states.adventure.physics;

import java.util.*;

/**
 * Created by h13730 on 10/28/2015.
 */
public class BoxCollection implements Collection<Box> {
  private List<Box> _list;
  private Set<Box> _set;

  public BoxCollection() {
    _list = new ArrayList<>();
    _set = new HashSet<>();
  }

  @Override
  public int size() {
    return _list.size();
  }

  @Override
  public boolean isEmpty() {
    return _list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return _set.contains(o);
  }

  @Override
  public Iterator<Box> iterator() {
    return _list.iterator();
  }

  @Override
  public Object[] toArray() {
    return _list.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return _list.toArray(a);
  }

  @Override
  public boolean add(Box box) {
    if(!_set.contains(box)) {
      _list.add(box);
      _set.add(box);
      return true;
    } else
      return false;
  }

  @Override
  public boolean remove(Object o) {
    boolean result = _set.remove(o);
    if(result)
      _list.remove(o);
    return result;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return _set.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends Box> c) {
    for(Box box:c) {
      if(!contains(box))
        add(box);
    }
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    for(Object o:c) {
      if(o instanceof Box) {
        Box box = (Box) o;
        if(contains(box))
          remove(box);
      }
    }
    return true;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new RuntimeException("Just don't use this. Not worth it.");
  }

  @Override
  public void clear() {
    _set.clear();
    _list.clear();
  }

  @Override
  public boolean equals(Object o) {
    throw new RuntimeException("Just don't use this. Not worth it.");
  }

  @Override
  public int hashCode() {
    return _list.hashCode() + _set.hashCode()*31;
  }
}

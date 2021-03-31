package com.github.hippalus.summarizer;


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public class ConcurrentHashSet<E> implements Set<E> {

  private final Map<E, Object> map;

  private static final Object OBJ = new Object();

  public ConcurrentHashSet(int size) {
    map = new ConcurrentHashMap<>(size);
  }

  public ConcurrentHashSet() {
    map = new ConcurrentHashMap<>();
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  //Throws: NullPointerException – if the specified key is null
  @Override
  public boolean contains(Object o) {
    return map.containsKey(o);
  }

  @Override
  public @NotNull Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  @Override
  public Object[] toArray() {
    return map.keySet().toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return map.keySet().toArray(a);
  }

  @Override
  public boolean add(E e) {
    return map.put(e, OBJ) == null;
  }

  //Throws:NullPointerException – if the specified key is null
  @Override
  public boolean remove(Object o) {
    return map.remove(o) != null;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return map.keySet().containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    boolean changed = false;
    for (E e : c) {
      if (map.put(e, OBJ) == null) {
        changed = true;
      }
    }
    return changed;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    map.clear();
  }


}

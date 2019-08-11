package com.x3platform;

/**
 * 引用对象
 *
 * @author ruanyu
 */
public final class RefObject<T>
{
  public T value;

  public RefObject(T refTarget)
  {
    value = refTarget;
  }
}

package org.udg.caes.stockmarket.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 12/11/13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class EntityNotFound extends Exception {
  public EntityNotFound() { super(); }
  public EntityNotFound(String message) { super(message); }
  public EntityNotFound(String message, Throwable cause) { super(message, cause); }
  public EntityNotFound(Throwable cause) { super(cause); }
}

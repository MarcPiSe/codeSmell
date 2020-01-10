package org.udg.caes.stockmarket.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 12/11/13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class InvalidOperation extends Exception {
  public InvalidOperation() { super(); }
  public InvalidOperation(String message) { super(message); }
  public InvalidOperation(String message, Throwable cause) { super(message, cause); }
  public InvalidOperation(Throwable cause) { super(cause); }
}

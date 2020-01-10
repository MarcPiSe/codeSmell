package org.udg.caes.stockmarket.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 12/11/13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class ElementNotExists extends Exception {
  public ElementNotExists() { super(); }
  public ElementNotExists(String message) { super(message); }
  public ElementNotExists(String message, Throwable cause) { super(message, cause); }
  public ElementNotExists(Throwable cause) { super(cause); }
}

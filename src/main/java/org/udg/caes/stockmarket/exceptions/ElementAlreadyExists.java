package org.udg.caes.stockmarket.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 12/11/13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class ElementAlreadyExists extends Exception {
  public ElementAlreadyExists() { super(); }
  public ElementAlreadyExists(String message) { super(message); }
  public ElementAlreadyExists(String message, Throwable cause) { super(message, cause); }
  public ElementAlreadyExists(Throwable cause) { super(cause); }
}

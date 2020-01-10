package org.udg.caes.stockmarket.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 12/11/13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class StockNotFound extends Exception {
  public StockNotFound() { super(); }
  public StockNotFound(String message) { super(message); }
  public StockNotFound(String message, Throwable cause) { super(message, cause); }
  public StockNotFound(Throwable cause) { super(cause); }
}

package org.udg.caes.stockmarket.EXTERNAL;

import org.udg.caes.stockmarket.exceptions.StockNotFound;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 22/10/13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public interface StockMarket {
  public String getName();
  public Double getPrice(String stockName) throws StockNotFound;
  boolean hasStock(String name);

  ArrayList<String> getAllStocks();
}

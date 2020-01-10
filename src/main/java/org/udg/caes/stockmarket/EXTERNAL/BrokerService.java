package org.udg.caes.stockmarket.EXTERNAL;

import org.udg.caes.stockmarket.Order;
import org.udg.caes.stockmarket.exceptions.StockNotFound;

import java.util.List;

/**
 * Created by imartin on 14/12/16.
 */
public interface BrokerService {
  void send(Order o);
  List<StockMarket> getMarkets();
  Double getPrice(String instrument) throws StockNotFound;
}

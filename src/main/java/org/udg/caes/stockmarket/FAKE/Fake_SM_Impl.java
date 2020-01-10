package org.udg.caes.stockmarket.FAKE;

import org.udg.caes.stockmarket.EXTERNAL.StockMarket;
import org.udg.caes.stockmarket.exceptions.StockNotFound;

import java.util.ArrayList;

// Fake class
public class Fake_SM_Impl implements StockMarket {

  String mName;
  ArrayList<String> mStocks = new ArrayList<String>();

  public Fake_SM_Impl(String name) {
    mName = name;
  }

  // This is a fake method that it is only here for the stub
  // In a real implementation stock names should be obtained from a remote service
  public void addStocks(ArrayList<String> stocks) {
    mStocks = stocks;
  }

  public String getName() {
    return mName;
  }

  public Double getPrice(String name) throws StockNotFound {
    // This is fake code. Here we would need a connection to a broker
    if (mStocks.contains(name)) {
      return  Fake_BS_Impl.getRandomStockPrice();
    }
    throw new StockNotFound();
  }

  public boolean hasStock(String name) {
    return mStocks.contains(name);
  }

  public ArrayList<String> getAllStocks() {
    return mStocks;
  }
}

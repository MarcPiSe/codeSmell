package org.udg.caes.stockmarket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 22/10/13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
public class Portfolio implements Iterable<Stock> {
  private String mId;
  private User mUser;
  private List<Stock> mStocks = new ArrayList<Stock>();
  private List<Gold> mGold = new ArrayList<Gold>();
  private List<Silver> mSilver = new ArrayList<Silver>();

  public Portfolio(User u, String id) {
    mUser = u;
    mId = id;
  }

  public Iterator<Stock> iterator() {
    Iterator<Stock> iprof = mStocks.iterator();
    return iprof;
  }

  public User getUser() {
    return mUser;
  }

  public void setUser(User mUser) {
    this.mUser = mUser;
  }

  public String getId() {
    return mId;
  }

  public void setId(String mId) {
    this.mId = mId;
  }

  public List<Stock> getStocks() {
    return mStocks;
  }

  public void setStocks(List<Stock> stocks) {
    this.mStocks = stocks;
  }

  public void addStock(Stock stock) {
    mStocks.add(stock);
  }

  public Boolean hasStock(String ticker) {
    for (Stock s: mStocks)
      if (s.getTicker().equals(ticker))
        return true;
    return false;
  }

  public List<Gold> getGold() {
    return mGold;
  }

  public List<Silver> getSilver() {
    return mSilver;
  }

  public double getBuyPrice() {
    double p = 0.0;

    for (Stock s : mStocks)
      p += s.getValue();
    for (Gold g : mGold)
      p += g.getValuation();
    for (Silver s : mSilver)
      p += s.getValuation();

    return p;
  }
}

package org.udg.caes.stockmarket;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 22/10/13
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class Stock {
  private String ticker;

  private int mQuantity;
  private double mBuyPrice;

  public double getBuyPrice() {
    return mBuyPrice;
  }

  public void setBuyPrice(double mBuyPrice) {
    this.mBuyPrice = mBuyPrice;
  }

  public Stock(String ticker, int quantity, double price) {
    this.ticker = ticker;
    this.mQuantity = quantity;
    this.mBuyPrice = price;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getQuantity() {
    return mQuantity;
  }

  public void setQuantity(int quantity) {
    this.mQuantity = quantity;
  }

  public double getPrice() { return mBuyPrice; }

  public double getValue() { return mQuantity * mBuyPrice; }
}

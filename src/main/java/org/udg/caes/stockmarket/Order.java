package org.udg.caes.stockmarket;

import org.udg.caes.stockmarket.EXTERNAL.BrokerService;

import java.util.Date;

public class Order {
  private String id;

  Portfolio portfolio;

  int type;
  public static final int MARKET = 1; // Buy at the current price
  public static final int PRICE = 2; // Buy when price is lower than targetPrice

  // Goal quantity to buy (maximum)
  int quantity;
  // Goal price (maximum)
  double targetPrice;
  // Limit date to execut the order
  Date limit;
  // Company to buy
  String ticker;

  int status;
  public static final int ACCEPTED = 1;
  public static final int PROCESSING = 2;
  public static final int COMPLETED = 3;
  public static final int PARTIALLY_COMPLETED = 4;
  public static final int CANCELLED = 5;
  public static final int TIMEOUT = 6;
  public static final int REJECTED = 7;

  int orderType;
  public static final int STOCK = 1;
  public static final int GOLD = 2;
  public static final int SILVER = 3;

  // Amount of stocks actually bought
  private int efQuant;
  // Buy price
  private double efPrice;

  public Order(Portfolio pf, int q, double tp, Date li, String t) {
    status = Order.PROCESSING;
    portfolio = pf;
    quantity = q;
    tp = tp;
    type = PRICE;
    ticker = t;
    limit = li;
    orderType = STOCK;
  }

  public Order(Portfolio pf, int q, Date l, String t) {
    status = Order.PROCESSING;
    portfolio = pf;
    quantity = q;
    type = MARKET;
    ticker = t;
    limit = l;
    orderType = STOCK;
  }

  public Order(Portfolio pf, int q, Date l, int material) {
    status = Order.PROCESSING;
    portfolio = pf;
    quantity = q;
    type = MARKET;
    limit = l;
    orderType = material;
  }


  /**
   * Send the order and a callback to be used once the stock service receives the response from the broker
   * @param bs
   * @return true if the order has been accepted
   * @throws NoSuchMethodException
   */
  public void send(BrokerService bs) {
    bs.send(this);
  }

  public String getId() {
    return id;
  }

  public void setId(String i) {
    this.id = i;
  }

  public int getOrderType() { return orderType; }

  public String getTicker() {
    return ticker;
  }

  public int getEfQuant() {
    return efQuant;
  }

  public double getEfPrice() {
    return efPrice;
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }

  public int getStatus() {
    return status;
  }

  public void setEfQuant(int eq) {
    this.efQuant = eq;
  }

  public void setEfPrice(double ep) {
    this.efPrice = ep;
  }

  public void setStatus(int s) {
    this.status = s;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getType() {
    return type;
  }
}

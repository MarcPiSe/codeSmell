package org.udg.caes.stockmarket;

/**
 * Created by imartin on 15/12/16.
 */
public class OrderStatusEvent {
  private int previousStatus;
  private Order order;

  public OrderStatusEvent(Order order, int previousStatus) {
    this.order = order;
    this.previousStatus = previousStatus;
  }

  public Order getOrder() {
    return order;
  }

  public int getPreviousStatus() {
    return previousStatus;
  }

  public void setPreviousStatus(int previousStatus) {
    this.previousStatus = previousStatus;
  }
}

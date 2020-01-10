package org.udg.caes.stockmarket;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.udg.caes.stockmarket.EXTERNAL.BrokerService;
import org.udg.caes.stockmarket.EXTERNAL.StockMarket;
import org.udg.caes.stockmarket.FAKE.Fake_PS_MySQL;
import org.udg.caes.stockmarket.exceptions.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class UtilsService {

  BrokerService mBS;
  EventBus mEB;

  private Integer orderId = 0;

  @Inject
  public UtilsService(BrokerService bs, EventBus eb) {
    mBS = bs;
    mEB = eb;
    mEB.register(this);
  }

  public ArrayList<String> searchStocks(String substring) {
    ArrayList<String> r = new ArrayList<String>();

    for (StockMarket sm: mBS.getMarkets())
      for (String s : sm.getAllStocks())
        if (s.contains(substring))
          r.add(s);

    return r;
  }

  public List<String> searchStocksWithValue(double price) throws BrokerInternalError {
    ArrayList<String> r = new ArrayList<String>();

    for (StockMarket sm: mBS.getMarkets())
      for (String s : sm.getAllStocks())
        try {
          if (mBS.getPrice(s) > price)
            r.add(s);
        }
        catch (StockNotFound stockNotFound) {
          throw new BrokerInternalError();
        }

    return r;
  }

  @Subscribe public void processOrderStatusChange(OrderStatusEvent event) throws EntityNotFound, ElementNotExists, InvalidOperation {
    Fake_PS_MySQL ps = new Fake_PS_MySQL();
    Order order = event.getOrder();
    if (order.getStatus() == Order.PROCESSING) {
      ps.saveOrder(order);
    } else if (event.getPreviousStatus() == Order.PROCESSING && (order.getStatus() == Order.COMPLETED || order.getStatus() == Order.PARTIALLY_COMPLETED)) {
      Portfolio portfolio = order.getPortfolio();
      if (order.getOrderType() == Order.STOCK)
        portfolio.addStock(new Stock(order.getTicker(), order.getEfQuant(), order.getEfPrice()));
      else if (order.getOrderType() == Order.GOLD)
        portfolio.getGold().add(new Gold(order.getEfQuant(), order.getEfPrice()));
      else if (order.getOrderType() == Order.SILVER)
        portfolio.getSilver().add(new Silver(order.getEfQuant(), order.getEfPrice()));
      ps.saveOrder(order);
      ps.savePortfolio(portfolio);
    }
  }
}

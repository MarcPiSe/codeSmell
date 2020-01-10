package org.udg.caes.stockmarket.FAKE;

import com.google.common.eventbus.EventBus;
import org.udg.caes.stockmarket.EXTERNAL.BrokerService;
import org.udg.caes.stockmarket.EXTERNAL.StockMarket;
import org.udg.caes.stockmarket.Order;
import org.udg.caes.stockmarket.OrderStatusEvent;
import org.udg.caes.stockmarket.exceptions.StockNotFound;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imartin on 14/12/16.
 */
public class Fake_BS_Impl implements BrokerService {

  @Inject
  EventBus mEB;

  List<StockMarket> mMarkets = new ArrayList<StockMarket>();

  private Integer mOrderId = 0;

  public Fake_BS_Impl() {
    Fake_SM_Impl nasdaq = new Fake_SM_Impl("NASDAQ");
    nasdaq.addStocks(new ArrayList<String>() {{ add("NVDA"); add("FB"); }});
    Fake_SM_Impl nyse = new Fake_SM_Impl("NYSE");
    nyse.addStocks(new ArrayList<String>() {{ add("AMD"); add("NOK"); }});
    Fake_SM_Impl ibex35 = new Fake_SM_Impl("IBEX35");
    ibex35.addStocks(new ArrayList<String>() {{ add("TEL"); add("ITX"); }});
    Fake_SM_Impl frankfurt = new Fake_SM_Impl("Frankfurt");
    frankfurt.addStocks(new ArrayList<String>() {{ add("NSU"); add("SIE"); }});

    mMarkets.add(nasdaq);
    mMarkets.add(nyse);
    mMarkets.add(ibex35);
    mMarkets.add(frankfurt);

  }

  public void send(Order o) {
    o.setId(mOrderId.toString());
    mOrderId++;
    int prevStatus = o.getStatus();
    o.setStatus(Order.COMPLETED);
    if (o.getOrderType() == Order.STOCK)
      o.setEfPrice(getRandomStockPrice());
    else if (o.getOrderType() == Order.GOLD)
      o.setEfPrice(getRandomGoldPrice());
    else if (o.getOrderType() == Order.SILVER)
      o.setEfPrice(getRandomSilverPrice());
    o.setEfQuant(o.getQuantity());
    mEB.post(new OrderStatusEvent(o, prevStatus));
  }

  public List<StockMarket> getMarkets() {
    return mMarkets;
  }

  public Double getPrice(String instrument) throws StockNotFound {
    if (instrument.equals("GOLD"))
      return getRandomGoldPrice();
    else if (instrument.contentEquals("SILVER"))
      return getRandomSilverPrice();
    else {
      for (StockMarket sm : mMarkets) {
        if (sm.hasStock(instrument))
          return sm.getPrice(instrument);
      }
      throw new StockNotFound();
    }
  }

  static double getRandomStockPrice() { return 50 + 100 * Math.random(); }
  static double getRandomGoldPrice() { return 500 + 200 * Math.random(); }
  static double getRandomSilverPrice() { return 100 + 150 * Math.random(); }

}

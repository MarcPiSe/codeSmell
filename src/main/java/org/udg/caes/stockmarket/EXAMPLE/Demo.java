package org.udg.caes.stockmarket.EXAMPLE;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.udg.caes.stockmarket.*;
import org.udg.caes.stockmarket.exceptions.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by imartin on 15/12/16.
 */
class Demo {
  BusinessService mBS;
  UtilsService mUS;
  EventBus mEB;

  User mU1, mU2;
  private User mU3;

  UserGroup mRootG;

  @Inject
  public Demo(BusinessService bs, UtilsService us, EventBus eb) {
    mUS = us;
    mBS = bs;
    mEB = eb;
    mEB.register(this);
  }

  public void doTest() throws ElementNotExists, InvalidOperation, ElementAlreadyExists, EntityNotFound, NoSuchMethodException {
    mU1 = new User("Jordi");
    mU2 = new User("Pep");
    mU3 = new User("Nacho");

    mBS.saveUser(mU1);
    mBS.saveUser(mU2);
    mBS.saveUser(mU3);

    Portfolio p11 = new Portfolio(mU1, "USA1");
    Portfolio p12 = new Portfolio(mU1, "USA2");
    mBS.savePortfolio(p11);
    mBS.savePortfolio(p12);

    Portfolio p21 = new Portfolio(mU2, "SPAIN");
    Portfolio p22 = new Portfolio(mU2, "USA");
    Portfolio p23 = new Portfolio(mU2, "MIX");
    mBS.savePortfolio(p21);
    mBS.savePortfolio(p22);
    mBS.savePortfolio(p23);

    Portfolio p31 = new Portfolio(mU3, "MIX2");
    mBS.savePortfolio(p31);

    UserGroup mRootG = new UserGroup("ROOT");
    UserGroup g1 = new UserGroup("G1");

    g1.addUser(mU1);
    g1.addUser(mU2);

    mRootG.addUser(mU3);
    mRootG.addGroup(g1);

    mBS.saveGroup(mRootG);
    mBS.saveGroup(g1);

    Date now = new Date();

    Order o11 = new Order(p11, 100, new Date(now.getTime() + 86400000L), "NVDA");
    mBS.placeOrder(o11);

    Order o12 = new Order(p12, 300, new Date(now.getTime() + 86400000L), "NOK");
    mBS.placeOrder(o12);

    Order o31 = new Order(p31, 200, new Date(now.getTime() + 86400000L), "FB");
    mBS.placeOrder(o31);

    Order o21 = new Order(p21, 200, new Date(now.getTime() + 86400000L), "NVDA");
    mBS.placeOrder(o21);

    Order o22 = new Order(p22, 225, new Date(now.getTime() + 86400000L), "ITX");
    mBS.placeOrder(o22);

    Order o23 = new Order(p23, 10, new Date(now.getTime() + 86400000L), Order.GOLD);
    mBS.placeOrder(o23);

    Order o24 = new Order(p23, 20, new Date(now.getTime() + 86400000L), Order.SILVER);
    mBS.placeOrder(o24);

    Order o25 = new Order(p23, 225, new Date(now.getTime() + 86400000L), "TEL");
    mBS.placeOrder(o25);

  }

  @Subscribe
  void orderChange(OrderStatusEvent e) throws Exception, BrokerInternalError {
    Order o = e.getOrder();
    Portfolio p = o.getPortfolio();
    if (o.getOrderType() == Order.STOCK)
      p.addStock(new Stock(o.getTicker(), o.getEfQuant(), o.getEfPrice()));
    else if (o.getOrderType() == Order.GOLD)
      p.getGold().add(new Gold(o.getEfQuant(), o.getEfPrice()));
    else if (o.getOrderType() == Order.SILVER)
      p.getSilver().add(new Silver(o.getEfQuant(), o.getEfPrice()));
    mBS.savePortfolio(p);

    if (o.getOrderType() == Order.STOCK && o.getTicker().equals("TEL")) {

      System.out.println("Searching best portfolio for user 1 ...");
      Portfolio pbest = mBS.getBestPortfolio(mU1.getId());
      System.out.println("... found: " + pbest.getId());

      System.out.println("Finding common stocks in users 1 and 2 ...");
      List<String> common = mBS.getCommonStocks(mU1.getId(), mU2.getId());
      for (String s : common)
        System.out.println("   " + s);

      System.out.println("Searching stocks containing \"N\" ...");
      List<String> ss1 = mUS.searchStocks("N");
      for (String s : ss1)
        System.out.println("   " + s);

      System.out.println("Computing real-time valuation of user 2 ...");
      Double v = mBS.getUserValuation(mU2.getId());
      System.out.println("   " + v.toString());

      System.out.println("Finding best portfolio of root group ...");
      pbest = mBS.getBestGroupPortfolio("ROOT", null, -1.0);
      System.out.println("... found: " + pbest.getId());

      System.out.println("Bye!");
    }
  }

}

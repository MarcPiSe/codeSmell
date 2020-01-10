package org.udg.caes.stockmarket;

import org.udg.caes.stockmarket.EXTERNAL.BrokerService;
import org.udg.caes.stockmarket.EXTERNAL.PersistenceService;
import org.udg.caes.stockmarket.exceptions.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the business logic of the app.
 * It resembles a EJB from Java EE 7
 */
public class BusinessService {
  private PersistenceService persistenceService;
  private BrokerService brokerService;
  private Integer orderId;

  @Inject
  public BusinessService(PersistenceService ps, BrokerService bs) {
    persistenceService = ps;
    brokerService = bs;
    orderId = 0;
  }

  public User getUser(String id) throws EntityNotFound {
    return persistenceService.getUser(id);
  }

  public Portfolio getPortfolio(String userId, String name) throws EntityNotFound, ElementNotExists {
    User u = persistenceService.getUser(userId);
    return u.getPortfolio(name);
  }

  public void saveUser(User u) {
    persistenceService.saveUser(u);
  }

  public void saveGroup(UserGroup g) {
    persistenceService.saveUserGroup(g);
  }

  public void savePortfolio(Portfolio p) throws ElementNotExists, EntityNotFound, ElementAlreadyExists, InvalidOperation {
    persistenceService.savePortfolio(p);
  }

  public Boolean hasStock(String userId, String ticker) throws EntityNotFound {
    User u = persistenceService.getUser(userId);
    for (Portfolio p : u.getAllPortfolios()) {
      if (p.hasStock(ticker))
        return true;
    }
    return false;
  }

  // Computes the real-time value of the users assets
  public Double getUserValuation(String userId) throws Exception {
    User u = persistenceService.getUser(userId);
    double v = 0.0;
    for (Portfolio p : u.getAllPortfolios()) {
      for (Stock s : p)
        v += s.getQuantity() * brokerService.getPrice(s.getTicker());
      for (Gold g : p.getGold())
        v += g.getmAmountOz() * brokerService.getPrice("GOLD");
      for (Silver s : p.getSilver())
        v += s.getmAmountOz() * brokerService.getPrice("SILVER");
    }

    return v;
  }

  // Computes the real-time value of the group assets
  public Double getUserGroupValuation(String userId) throws Exception {
    UserGroup ug = persistenceService.getUserGroup(userId);
    double v = 0.0;
    for (User u : ug.getUsers()) {
      for (Portfolio p : u.getAllPortfolios()) {
        for (Stock s : p)
          v += s.getQuantity() * brokerService.getPrice(s.getTicker());
        for (Gold g : p.getGold())
          v += g.getmAmountOz() * brokerService.getPrice("GOLD");
        for (Silver s : p.getSilver())
          v += s.getmAmountOz() * brokerService.getPrice("SILVER");
      }
    }
    for (UserGroup uu : ug.getGroups())
      v += this.getUserGroupValuation(uu.getId());

    return v;
  }

  // Finds the portfolio with the best real-time valuation
  public Portfolio getBestPortfolio(String userId) throws EntityNotFound, StockNotFound {
    User u = persistenceService.getUser(userId);
    double max = -Double.MAX_VALUE;
    Portfolio result = null;
    for (Portfolio p : u.getAllPortfolios()) {
      double profit = 0.0;
      for (Stock s : p)
        profit += s.getQuantity() * (brokerService.getPrice(s.getTicker()) - s.getBuyPrice());
      for (Gold g : p.getGold())
        profit += g.getmAmountOz() * (brokerService.getPrice("GOLD") - g.getValuation());
      for (Silver s : p.getSilver())
        profit += s.getmAmountOz() * (brokerService.getPrice("SILVER") - s.getValuation());

      if (profit > max) {
        max = profit;
        result = p;
      }
    }
    return result;
  }

  // Finds the portfolio with the best real-time valuation
  public Portfolio getBestGroupPortfolio(String groupId, Portfolio pBest, double value) throws EntityNotFound, StockNotFound {
    UserGroup ug = persistenceService.getUserGroup(groupId);
    double max = value;
    for (User u : ug.getUsers()) {
      for (Portfolio p : u.getAllPortfolios()) {
        double profit = 0.0;
        for (Stock s : p)
          profit += s.getQuantity() * (brokerService.getPrice(s.getTicker()) - s.getBuyPrice());
        for (Gold g : p.getGold())
          profit += g.getmAmountOz() * (brokerService.getPrice("GOLD") - g.getValuation());
        for (Silver s : p.getSilver())
          profit += s.getmAmountOz() * (brokerService.getPrice("SILVER") - s.getValuation());

        if (profit > max) {
          max = profit;
          pBest = p;
        }
      }
    }

    for (UserGroup uu : ug.getGroups()) {
      pBest = this.getBestGroupPortfolio(uu.getId(), pBest, max);
    }

    return pBest;
  }


  public List<String> getCommonStocks(String userId1, String userId2) throws EntityNotFound {
    User u1 = persistenceService.getUser(userId1);
    User u2 = persistenceService.getUser(userId2);
    ArrayList<String> stocks = new ArrayList<String>();
    for (Portfolio p : u1.getAllPortfolios()) {
      for (Stock s : p)
        if (!stocks.contains(s.getTicker()) && u2.hasStock(s.getTicker()))
          stocks.add(s.getTicker());
    }
    return stocks;
  }

  /**
   * This method sends an Order for a give User
   *
   * @param o Order
   * @throws OrderNotSent
   */
  public void placeOrder(Order o) throws NoSuchMethodException {
    orderId++;
    o.setId(orderId.toString());
    o.setStatus(Order.PROCESSING);
    persistenceService.saveOrder(o);
    o.send(brokerService);
  }
}

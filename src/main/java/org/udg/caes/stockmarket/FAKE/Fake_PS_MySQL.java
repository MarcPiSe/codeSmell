package org.udg.caes.stockmarket.FAKE;

import org.udg.caes.stockmarket.*;
import org.udg.caes.stockmarket.exceptions.ElementAlreadyExists;
import org.udg.caes.stockmarket.exceptions.ElementNotExists;
import org.udg.caes.stockmarket.exceptions.EntityNotFound;
import org.udg.caes.stockmarket.exceptions.InvalidOperation;
import org.udg.caes.stockmarket.EXTERNAL.PersistenceService;
import org.udg.caes.stockmarket.Portfolio;
import org.udg.caes.stockmarket.User;

import java.util.HashMap;

// This is a fake class
public class Fake_PS_MySQL implements PersistenceService {
  // These two fields mimic a database
  private HashMap<String, User> mUsers = new HashMap<String, User>();
  private HashMap<String, Portfolio> mPortfolios = new HashMap<String, Portfolio>();
  private HashMap<String, Order> mOrders = new HashMap<String, Order>();
  private HashMap<String, UserGroup> mGroups = new HashMap<String, UserGroup>();
  
  private Integer orderId = 0;

  public User getUser(String id) throws EntityNotFound {
    if (mUsers.containsKey(id))
      return mUsers.get(id);
    else
      throw new EntityNotFound("User %id not found".format(id));
  }

  public UserGroup getUserGroup(String id) throws EntityNotFound {
    if (mGroups.containsKey(id))
      return mGroups.get(id);
    else
      throw new EntityNotFound("Group %id not found".format(id));
  }


  public Portfolio getPortfolio(String id) throws EntityNotFound {
    if (mPortfolios.containsKey(id))
      return mPortfolios.get(id);
    else
      throw new EntityNotFound("User %id not found".format(id));
  }

  public void saveUser(User u) {
    mUsers.put(u.getId(), u);
  }

  public void saveUserGroup(UserGroup u) {
    mGroups.put(u.getId(), u);
  }

  public void savePortfolio(Portfolio p) throws EntityNotFound, ElementNotExists, InvalidOperation {
    User u = getUser(p.getUser().getId());
    try {
    u.replacePortfolio(p);
    } catch (ElementNotExists e) {
      try {
        u.addPortfolio(p);
      } catch (ElementAlreadyExists elementAlreadyExists) {
        throw new InvalidOperation();
      }
    }
    mPortfolios.put(p.getId(), p);
  }

  public void saveOrder(Order o) {
    mOrders.put(o.getId(), o);
  }

  public Order getOrder(String id) throws EntityNotFound {
    if (mOrders.containsKey(id))
      return mOrders.get(id);
    else
      throw new EntityNotFound("Order %id not found".format(id));
  }

}

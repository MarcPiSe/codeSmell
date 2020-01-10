package org.udg.caes.stockmarket;

import org.udg.caes.stockmarket.exceptions.ElementAlreadyExists;
import org.udg.caes.stockmarket.exceptions.ElementNotExists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: imartin
 * Date: 12/11/13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class User {
  String mId;
  List<Portfolio> mPort = new ArrayList<Portfolio>();

  public User(String id) {
    mId = id;
  }

  public boolean hasPortfolio(String pid) {
    for (Portfolio p: mPort)
      if (p.getId().equals(pid))
        return true;
    return false;
  }

  public Portfolio getPortfolio(String id) throws ElementNotExists {
    for (Portfolio p: mPort)
      if (p.getId().equals(id))
        return p;
    throw new ElementNotExists();
  }

  public List<Portfolio> getAllPortfolios() {
    return mPort;
  }

  public String getId() {
    return mId;
  }

  public void replacePortfolio(Portfolio portfolio) throws ElementNotExists {
    for (Portfolio p: mPort)
      if (p.getId().equals(portfolio.getId()))
      {
        mPort.remove(p);
        mPort.add(portfolio);
        return;
      }
    throw new ElementNotExists();
  }

  public void addPortfolio(Portfolio p) throws ElementAlreadyExists {
    if (hasPortfolio(p.getId()))
      throw new ElementAlreadyExists();
    mPort.add(p);
  }

  public boolean hasStock(String name) {
    for (Portfolio p: mPort)
      if (p.hasStock(name))
        return true;
    return false;
  }
}

package org.udg.caes.stockmarket.EXTERNAL;

import org.udg.caes.stockmarket.Order;
import org.udg.caes.stockmarket.Portfolio;
import org.udg.caes.stockmarket.User;
import org.udg.caes.stockmarket.UserGroup;
import org.udg.caes.stockmarket.exceptions.ElementAlreadyExists;
import org.udg.caes.stockmarket.exceptions.ElementNotExists;
import org.udg.caes.stockmarket.exceptions.EntityNotFound;
import org.udg.caes.stockmarket.exceptions.InvalidOperation;

/**
 * This class has a fake implementation.
 * In a real example, some of the methods should access a database, so we don't want to unit test them
 * IMPORTANT: for the sake of simplicity, we consider that when an object is saved, all its
 * referenced objects are also saved, including collections containing objects.
 */
public interface PersistenceService {
  User getUser(String id) throws EntityNotFound;
  Portfolio getPortfolio(String id) throws EntityNotFound;
  void saveUser(User u);

  void saveUserGroup(UserGroup u);

  void savePortfolio(Portfolio p) throws EntityNotFound, ElementNotExists, ElementAlreadyExists, InvalidOperation;

  void saveOrder(Order o);
  Order getOrder(String id) throws EntityNotFound;

  UserGroup getUserGroup(String userGroupId) throws EntityNotFound;
}

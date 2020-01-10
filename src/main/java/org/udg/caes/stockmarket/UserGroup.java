package org.udg.caes.stockmarket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imartin on 13/12/16.
 */
public class UserGroup {
  private List<User> mUsers = new ArrayList<User>();
  private List<UserGroup> mGroups = new ArrayList<UserGroup>();
  private String id;

  public List<User> getUsers() { return mUsers; }
  public List<UserGroup> getGroups() { return mGroups; }

  public UserGroup(String id) {
    this.id = id;
  }

  public void addUser(User u) { mUsers.add(u); }
  public void addGroup(UserGroup ug) { mGroups.add(ug); }

  public String getId() {
    return id;
  }
}

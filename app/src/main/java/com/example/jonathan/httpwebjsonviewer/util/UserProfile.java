package com.example.jonathan.httpwebjsonviewer.util;

import java.util.List;

public class UserProfile {
  private String m_userName;
  private String m_email;
  private double m_amount;
  private List<String> m_friends;

  public UserProfile() {}

  public String getUserName() { return m_userName; }
  public void setUserName(String userName) { m_userName = userName; }

  public String getEmail() { return m_email; }
  public void setEmail(String email) { m_email = email; }

  public double getAmount() { return m_amount; }
  public void setAmount(double amount) { m_amount = amount; }

  public List<String> getFriends() { return m_friends; }
  public void setFriends(List<String> friends) { m_friends = friends; }
}

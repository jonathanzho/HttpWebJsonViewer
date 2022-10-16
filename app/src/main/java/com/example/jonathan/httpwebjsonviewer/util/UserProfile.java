package com.example.jonathan.httpwebjsonviewer.util;

import java.util.List;

public class UserProfile {
  private String mUserName;
  private String mEmail;
  private double mAmount;
  private List<String> mFriendList;

  public UserProfile() {}

  public String getUserName() { return mUserName; }
  public void setUserName(String userName) { mUserName = userName; }

  public String getEmail() { return mEmail; }
  public void setEmail(String email) { mEmail = email; }

  public double getAmount() { return mAmount; }
  public void setAmount(double amount) { mAmount = amount; }

  public List<String> getFriends() { return mFriendList; }
  public void setFriends(List<String> friends) { mFriendList = friends; }
}

package com.example.jonathan.httpwebjsonviewer.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfile {
  // SerializedName appears to be needed to make fromJson() work:
  @SerializedName("userName") private String mUserName;
  @SerializedName("email") private String mEmail;
  @SerializedName("amount") private double mAmount;
  @SerializedName("friends") private List<String> mFriendList;

  public UserProfile() {}

  public String getUserName() { return mUserName; }
  public void setUserName(String userName) { mUserName = userName; }

  public String getEmail() { return mEmail; }
  public void setEmail(String email) { mEmail = email; }

  public double getAmount() { return mAmount; }
  public void setAmount(double amount) { mAmount = amount; }

  public List<String> getFriendList() { return mFriendList; }
  public void setFriendList(List<String> friendList) { mFriendList = friendList; }
}

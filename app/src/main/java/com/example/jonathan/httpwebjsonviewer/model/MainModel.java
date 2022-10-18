package com.example.jonathan.httpwebjsonviewer.model;

import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {
  private List<UserProfile> mUserProfileList;

  public List<UserProfile> getUserProfileList() {
    return mUserProfileList;
  }

  public void setUserProfileList(List<UserProfile> userProfileList) {
    mUserProfileList = userProfileList;

    setChanged();
    notifyObservers();
  }
}

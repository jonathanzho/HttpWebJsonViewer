package com.example.jonathan.httpwebjsonviewer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.jonathan.httpwebjsonviewer.R;
import com.example.jonathan.httpwebjsonviewer.model.UserProfile;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<
    MyRecyclerViewAdapter.ViewHolder> {
  private List<UserProfile> mUserProfileList;

  public MyRecyclerViewAdapter(List<UserProfile> userProfileList) {
    mUserProfileList = userProfileList;
  }

  @Override
  public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
    View rowItem = LayoutInflater.from(parent.getContext()).inflate(
        viewType, parent, false);

    return new ViewHolder(rowItem);
  }

  @Override
  public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
    UserProfile up = mUserProfileList.get(position);
    holder.mUserNameTextView.setText(up.getUserName());
    holder.mEmailTextView.setText(up.getEmail());
    holder.mAmountTextView.setText(String.valueOf(up.getAmount()));
    List<String> friendList = up.getFriendList();
    if (friendList != null) {
      holder.mFriendsTextView.setText(String.join(",", friendList));
    } else {
      holder.mFriendsTextView.setText("no friends");
    }
  }

  @Override
  public int getItemCount() {
    return mUserProfileList.size();
  }

  @Override
  public int getItemViewType(int position) {
    return R.layout.item_view;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {
    private TextView mUserNameTextView;
    private TextView mEmailTextView;
    private TextView mAmountTextView;
    private TextView mFriendsTextView;

    public ViewHolder(View view) {
      super(view);
      view.setOnClickListener(this);

      mUserNameTextView = view.findViewById(R.id.user_name_text_view);
      mEmailTextView = view.findViewById(R.id.email_text_view);
      mAmountTextView = view.findViewById(R.id.amount_text_view);
      mFriendsTextView = view.findViewById(R.id.friends_text_view);
    }

    @Override
    public void onClick(View view) {
      Toast.makeText(view.getContext(),
          "position=[" + getLayoutPosition() +
          "], [" + mUserNameTextView.getText() +
          ", " + mEmailTextView.getText() +
          ", " + mAmountTextView.getText() +
          ", " + mFriendsTextView.getText() + "]",
          Toast.LENGTH_SHORT).show();
    }
  }
}


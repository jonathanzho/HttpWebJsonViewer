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
  private List<UserProfile> mData;

  public MyRecyclerViewAdapter(List<UserProfile> data) {
    mData = data;
  }

  @Override
  public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
    View rowItem = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.list_item_view, parent, false);

    return new ViewHolder(rowItem);
  }

  @Override
  public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
    holder.mUserNameTextView.setText(mData.get(position).getUserName());
  }

  @Override
  public int getItemCount() {
    return mData.size();
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
      Toast.makeText(view.getContext(), "position : " + getLayoutPosition() +
          " text : " + mUserNameTextView.getText(), Toast.LENGTH_SHORT).show();
    }
  }
}


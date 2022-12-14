package com.example.jonathan.httpwebjsonviewer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jonathan.httpwebjsonviewer.adapter.MyRecyclerViewAdapter;
import com.example.jonathan.httpwebjsonviewer.model.MainModel;
import com.example.jonathan.httpwebjsonviewer.model.UserProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
  private static final String TAG = "HWJV: MainActivity";

  // URL in jonathanzho's github account:
  private static final String TEST_JSON_URL =
      "https://raw.githubusercontent.com/jonathanzho/resFiles/master/json/user_profiles.json";

  // MVC architecture:
  MainModel mMainModel;

  RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Connect with ModeL:
    mMainModel = new MainModel();
    mMainModel.addObserver(this);

    // Set up RecyclerView:
    mRecyclerView = findViewById(R.id.recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL));
  }

  @Override
  protected void onResume() {
    Log.d(TAG, "onResume");

    super.onResume();

    // Update adapter:
    mRecyclerView.setAdapter(new MyRecyclerViewAdapter(generateData()));

    // Get updated data:
    HwjvAsyncTask jsonTask = new HwjvAsyncTask();
    jsonTask.execute(TEST_JSON_URL);
  }

  private List<UserProfile> generateData() {
    Log.d(TAG, "generateData");

    List<UserProfile> userProfileList = mMainModel.getUserProfileList();

    if (userProfileList == null) {
      // If no userProfileList are available, use a dummy user profile:
      UserProfile up = new UserProfile();
      up.setUserName("no user name");
      up.setEmail("no email");
      up.setAmount(-1.0);
      up.setFriendList(Arrays.asList("no", "friends"));

      userProfileList = new ArrayList<>();
      userProfileList.add(up);
    }

    return userProfileList;
  }

  @Override
  public void update(Observable o, Object arg) {
    Log.d(TAG, "update");

    // Update adapter:
    mRecyclerView.setAdapter(new MyRecyclerViewAdapter(generateData()));
  }

  private class HwjvAsyncTask extends AsyncTask<String, String, String> {
    private final String TAG2 = "HWJV: HwjvAsyncTask";

    @Override
    protected void onPreExecute() {
      Log.d(TAG2, "onPreExecute");

      super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
      Log.d(TAG2, "doInBackground");

      String result = null;

      HttpURLConnection connection = null;
      BufferedReader reader = null;

      try {
        URL url = new URL(params[0]);
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream stream = connection.getInputStream();

        reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder buffer = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
          buffer.append(line);

          Log.d(TAG2, "line>" + line);
        }

        result = buffer.toString();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          connection.disconnect();
        }

        try {
          if (reader != null) {
            reader.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      return result;
    }

    @Override
    protected void onPostExecute(String result) {
      Log.d(TAG2, "onPostExecute: result=[" + result + "]");

      super.onPostExecute(result);

      // Set up Gson
      GsonBuilder gsonBuilder = new GsonBuilder();
      Gson gson = gsonBuilder.create();

      // Deserialize JSON string:
      Type listType = new TypeToken<List<UserProfile>>(){}.getType();
      List<UserProfile> userProfileList = gson.fromJson(result, listType);
      mMainModel.setUserProfileList(userProfileList);
      for (UserProfile up : userProfileList) {
        Log.d(TAG, "userName=[" + up.getUserName() +
            "], email=[" + up.getEmail() +
            "], amount=[" + up.getAmount() +
            "], friends=[" + up.getFriendList() + "]");
      }
    }
  }
}

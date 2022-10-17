package com.example.jonathan.httpwebjsonviewer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jonathan.httpwebjsonviewer.adapter.CustomAdapter;
import com.example.jonathan.httpwebjsonviewer.util.UserProfile;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "HWJV: MainActivity";

  // URL in jonathanzho's github account:
  private static final String TEST_JSON_URL =
      "https://raw.githubusercontent.com/jonathanzho/resFiles/master/json/user_profiles.json";

  ProgressDialog pd;
  TextView tvHW;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate: start");

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView recyclerView = findViewById(R.id.recycler_view);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new CustomAdapter(generateData()));
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    // Customization starts from here
/*
    tvHW = findViewById(R.id.tvHW);

    HwjvAsyncTask jsonTask = new HwjvAsyncTask();
    jsonTask.execute(TEST_JSON_URL);
*/
    Log.d(TAG, "onCreate: end");
  }

  private List<String> generateData() {
    List<String> data = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      data.add(String.valueOf(i) + "th Element");
    }
    return data;
  }

  private class HwjvAsyncTask extends AsyncTask<String, String, String> {
    private final String TAG2 = "HWJV: HwjvAsyncTask";

    @Override
    protected void onPreExecute() {
      Log.d(TAG2, "onPreExecute");

      super.onPreExecute();

      pd = new ProgressDialog(MainActivity.this);
      pd.setMessage("Please wait!");
      pd.setCancelable(false);
      pd.show();
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

        StringBuffer buffer = new StringBuffer();
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

      // Dismiss progress dialog
      if (pd.isShowing()) {
        pd.dismiss();
      }

      // Set up Gson
      GsonBuilder gsonBuilder = new GsonBuilder();
      Gson gson = gsonBuilder.create();

      // Deserialize JSON string:
      Type listType = new TypeToken<List<UserProfile>>(){}.getType();
      List<UserProfile> userProfileList = gson.fromJson(result, listType);
      for (UserProfile up : userProfileList) {
        Log.d(TAG, "userName=[" + up.getUserName() +
            "], email=[" + up.getEmail() +
            "], amount=[" + up.getAmount() +
            "], friends=[" + up.getFriendList() + "]");
      }

      // Display
      //tvHW.setText(result);
    }
  }
}

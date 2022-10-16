package com.example.jonathan.httpwebjsonviewer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jonathan.httpwebjsonviewer.util.UserProfile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    // Customization starts from here

    tvHW = findViewById(R.id.tvHW);

    HwjvAsyncTask jsonTask = new HwjvAsyncTask();
    jsonTask.execute(TEST_JSON_URL);

    Log.d(TAG, "onCreate: end");
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

      // Deserialize JSON
      UserProfile[] userProfiles = gson.fromJson(result, UserProfile[].class);

      int numUsers = userProfiles.length;

      Log.d(TAG2, "numUsers=[" + numUsers + "]");

      String user0Name = userProfiles[0].getUserName();
      String user0Email = userProfiles[0].getEmail();
      double user0Amount = userProfiles[0].getAmount();
      List<String> user0FriendList = userProfiles[0].getFriendList();

      // ??? actual:
      // null, null, 0.0, null
      // expected:
      // "Jonathan", "jonathan@abc.cpm", 12.34, ["Joe", "George", "Gary"]
      Log.d(TAG2, "user0Name=[" + user0Name + "], user0Email=[" + user0Email +
          "], user0Amount=[" + user0Amount + "], user0FriendList=[" + user0FriendList + "]");

      // Display
      tvHW.setText(result);
    }
  }
}

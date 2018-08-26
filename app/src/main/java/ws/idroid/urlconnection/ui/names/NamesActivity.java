package ws.idroid.urlconnection.ui.names;

import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.json.*;

import java.util.*;

import ws.idroid.urlconnection.*;
import ws.idroid.urlconnection.model.User;
import ws.idroid.urlconnection.util.NetworkUtil;

public class NamesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        new GetUsersListTask().execute();
    }

    private class GetUsersListTask extends AsyncTask<Void, Void, Void> {
        private List<User> namesList = new ArrayList<>();
        private User user = null;
        private int i;
        private String url = BuildConfig.SERVER_URL_PREFIX + "getUsers.php";

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Log.i(BuildConfig.TAG, "Request Url  = " + url);
                String jsonResult;
                try {
                    jsonResult = NetworkUtil.makeServiceCall(url);
                    Log.i("Server Response ", jsonResult);
                    JSONObject jsonUsersObject;
                    jsonUsersObject = new JSONObject(jsonResult);
                    JSONArray jsonUsersArray;
                    jsonUsersArray = jsonUsersObject.getJSONArray("Table");
                    for (int i = 0; i < jsonUsersArray.length(); i++) {
                        user = new User();
                        JSONObject jsonUser = jsonUsersArray.getJSONObject(i);
                        user.setId(jsonUser.getString("id"));
                        user.setName(jsonUser.getString("name"));
                        user.setEmail(jsonUser.getString("email"));

                        namesList.add(user);
                    }
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            LinearLayout linearLayout = findViewById(R.id.linear);

                for (i = 0; i < namesList.size(); i++) {
                    View view = getLayoutInflater()
                            .inflate(R.layout.row_main_item, null);
                    TextView tvName = view.findViewById(R.id.row_title);
                    tvName.setText(String.format("%s ) %s", namesList.get(i).getId(), namesList.get
                            (i).getName()));
                    linearLayout.addView(view);
                }

        }

    }

}

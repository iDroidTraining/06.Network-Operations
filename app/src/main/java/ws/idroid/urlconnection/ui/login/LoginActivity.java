package ws.idroid.urlconnection.ui.login;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ws.idroid.urlconnection.R;
import ws.idroid.urlconnection.constants.Constants;
import ws.idroid.urlconnection.ui.names.NamesActivity;
import ws.idroid.urlconnection.ui.registration.RegistrationActivity;
import ws.idroid.urlconnection.util.NetworkUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> new LoginTask().execute());
        Button btnRegister = findViewById(R.id.btn_login_register);
        btnRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,
                RegistrationActivity.class)));
    }

    private class LoginTask extends AsyncTask<Void, Void, String> {
        private String url = Constants.URL_PREFIX + "login.php?";
        private String Parameters;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                Parameters = "email=" + URLEncoder.encode(etEmail.getText().toString(), "UTF-8") +
                        "&password=" + URLEncoder.encode(etPassword.getText().toString(), "UTF-8");
                url = url + Parameters;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... arg0) {
            Log.i(Constants.TAG, "Request Url  = " + url);
            String jsonStr = "";
            try {
                jsonStr = NetworkUtil.makeServiceCall(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i(Constants.TAG, jsonStr);

            if (jsonStr != null) {
                return jsonStr;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equalsIgnoreCase("0")) {
                Toast.makeText(LoginActivity.this, "Logged in !!!!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, NamesActivity.class);
                startActivity(i);
            } else
                Toast.makeText(LoginActivity.this, "Wrong username or Password!!!", Toast
                        .LENGTH_LONG).show();
        }

    }

}

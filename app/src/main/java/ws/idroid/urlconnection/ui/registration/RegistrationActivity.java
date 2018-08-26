package ws.idroid.urlconnection.ui.registration;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ws.idroid.urlconnection.*;
import ws.idroid.urlconnection.ui.names.NamesActivity;
import ws.idroid.urlconnection.util.NetworkUtil;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
// .build();
//        StrictMode.setThreadPolicy(policy);

        initViews();
        initListeners();
        initProgressBar();
    }

    private void initProgressBar() {
        RelativeLayout layout = findViewById(R.id.rl_registration);

        progressBar = new ProgressBar(RegistrationActivity.this, null, android.R.attr
                .progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar, params);
        progressBar.setVisibility(View.GONE);

    }

    private void initListeners() {
        btnRegister.setOnClickListener(view -> new RegistrationTask().execute());
    }

    private void initViews() {
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        btnRegister = findViewById(R.id.btn_submit);
    }

    private class RegistrationTask extends AsyncTask<Void, Void, Boolean> {
        private String url = BuildConfig.SERVER_URL_PREFIX + "register.php?";
        private String Parameters;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
            super.onPreExecute();
            try {
                Parameters =
                        "name=" + URLEncoder.encode(etName.getText().toString(), "UTF-8") +
                                "&email=" + URLEncoder.encode(etEmail.getText().toString(),
                                "UTF-8") +
                                "&password=" + URLEncoder.encode(etPassword.getText().toString(),
                                "UTF-8");
                url = url + Parameters;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            Log.i("URL ", "URL  = " + url);
            String jsonStr;
            try {
                jsonStr = NetworkUtil.makeServiceCall(url);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return false;

            }
            Log.i("Server Response ", jsonStr);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressBar.setVisibility(View.GONE);

            Toast.makeText(RegistrationActivity.this, "Your information has been submitted " +
                    "successfully!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistrationActivity.this, NamesActivity.class));
        }
    }

}

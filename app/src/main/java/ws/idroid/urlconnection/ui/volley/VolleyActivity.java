package ws.idroid.urlconnection.ui.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import ws.idroid.urlconnection.R;
import ws.idroid.urlconnection.constants.Constants;

public class VolleyActivity extends AppCompatActivity {

    private TextView tvCourseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        initViews();
        makeVolleyRequest();
    }

    private void makeVolleyRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.URL_PREFIX + "getCourseName.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    tvCourseTitle.setText(String.format("Response is: %s", response));
                }, error -> tvCourseTitle.setText(R.string.error_getting_response));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void initViews() {
        tvCourseTitle = findViewById(R.id.tv_volley_course_title);
    }
}

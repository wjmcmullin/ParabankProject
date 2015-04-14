package com.parabank.parasoft.app.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parabank.parasoft.app.android.adts.Customer;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import static com.parabank.parasoft.app.android.Constants.NO_USER;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_HOST;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PORT;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private ImageButton btnConnectionSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        etUsername = (EditText)findViewById(R.id.etUsername);

        etPassword = (EditText)findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnConnectionSettings = (ImageButton)findViewById(R.id.btnConnectionSettings);
        btnConnectionSettings.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String username = etUsername.getText().toString();
        switch(v.getId()) {
            case R.id.btnLogin:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etUsername.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
                String password = etPassword.getText().toString();
                attemptLogin(username, password);
                break;
            case R.id.btnConnectionSettings:
                editConnectionSettings();
                break;
        }
    }

    private void attemptLogin(final String username, final String password) {
        final ProgressDialog loadingDialog = new ProgressDialog(this);
        //loadingDialog.setTitle(getString(R.string.dialog_login_load_title));
        loadingDialog.setMessage(getString(R.string.dialog_login_load));
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setCancelable(true);
        loadingDialog.show();

        SharedPreferences preferences = getSharedPreferences(PREFS_PARABANK, MODE_PRIVATE);
        //String protocol = preferences.getString(PREFS_PARABANK_PROTOCOL, getResources().getString(R.string.example_protocol));
        String host = preferences.getString(PREFS_PARABANK_HOST, getResources().getString(R.string.example_host));
        String port = preferences.getString(PREFS_PARABANK_PORT, getResources().getString(R.string.example_port));

        String loginUrl = Connection.generateLoginURL(host, port, username, password);
        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(loginUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                if (!loadingDialog.isShowing()) {
                    return;
                }

                loadingDialog.dismiss();

                Customer customer = null;
                try {
                    JSONObject obj = jsonObject.getJSONObject("customer");
                    customer = new Customer(obj);
                    login(customer);
                } catch (JSONException e) {
                    AlertDialog.Builder errorDialog = new AlertDialog.Builder(LoginActivity.this);
                    errorDialog.setTitle(R.string.dialog_login_error_title);
                    errorDialog.setMessage(e.getMessage());
                    errorDialog.setPositiveButton(R.string.global_action_okay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //...
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (!loadingDialog.isShowing()) {
                    return;
                }

                loadingDialog.dismiss();

                AlertDialog.Builder errorDialog = new AlertDialog.Builder(LoginActivity.this);
                errorDialog.setTitle(R.string.dialog_login_error_title);
                errorDialog.setMessage(responseString);
                errorDialog.setPositiveButton(R.string.global_action_okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //...
                    }
                });

                errorDialog.show();
            }
        });
    }

    private void editConnectionSettings() {
        Intent i = new Intent(this, ConnectionSettingsActivity.class);
        startActivity(i);
    }

    private void login(Customer customer) {

    }
}

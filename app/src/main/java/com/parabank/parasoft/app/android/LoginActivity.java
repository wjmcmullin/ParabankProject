package com.parabank.parasoft.app.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;

import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_HOST;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PORT;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PROTOCOL;

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
        String protocol = preferences.getString(PREFS_PARABANK_PROTOCOL, getResources().getString(R.string.example_protocol));
        String host = preferences.getString(PREFS_PARABANK_HOST, getResources().getString(R.string.example_host));
        String port = preferences.getString(PREFS_PARABANK_PORT, getResources().getString(R.string.example_port));

        Connect connection = new Connect();
        String str = connection.loginUrl(host, port, username, password);
        Log.e("parabank", connection.executeGet(str));
        /*RequestParams params = new RequestParams();
        params.put("_type", "json");

        final AsyncHttpClient client = new AsyncHttpClient();
        client.get(protocol + host + ":" + port + "/parabank/services/bank/login/" + username + "/" + password, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("parabank", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsona) {
                if (!loadingDialog.isShowing()) {
                    return;
                }

                loadingDialog.dismiss();

                login();

                long id = NO_USER;
                String name = null;
                try {
                    JSONObject json = jsona.getJSONObject(0);
                    JSONObject customer = json.getJSONObject("customer");
                    id = Long.parseLong(customer.getString("id"));
                    name = customer.getString("firstName");
                } catch (JSONException e) {
                    id = NO_USER;
                    name = null;
                } finally {
                    if (id == NO_USER) {
                        if (password != null) {
                            AlertDialog.Builder errorDialog = new AlertDialog.Builder(LoginActivity.this);
                            errorDialog.setTitle(R.string.dialog_login_error_title);
                            errorDialog.setMessage(R.string.dialog_login_error);
                            errorDialog.setPositiveButton(R.string.global_action_okay, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //...
                                }
                            });

                            errorDialog.show();
                        }
                    } else {
                        login();
                    }
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

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (!loadingDialog.isShowing()) {
                    return;
                }

                loadingDialog.dismiss();

                AlertDialog.Builder errorDialog = new AlertDialog.Builder(LoginActivity.this);
                errorDialog.setTitle(R.string.dialog_login_error_title);
                errorDialog.setMessage(throwable.getMessage());
                errorDialog.setPositiveButton(R.string.global_action_okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //...
                    }
                });

                errorDialog.show();
            }
        });*/
    }

    private void editConnectionSettings() {
        Intent i = new Intent(this, ConnectionSettingsActivity.class);
        startActivity(i);
    }

    private void login() {
        Log.e("parabank", "Login Accepted!");
    }
}

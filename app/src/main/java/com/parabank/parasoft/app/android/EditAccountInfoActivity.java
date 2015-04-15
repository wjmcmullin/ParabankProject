package com.parabank.parasoft.app.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parabank.parasoft.app.android.adts.Address;
import com.parabank.parasoft.app.android.adts.Customer;
import com.parabank.parasoft.app.android.adts.User;

import org.apache.http.Header;
import org.json.JSONObject;

import static com.parabank.parasoft.app.android.Constants.INTENT_USER;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_HOST;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PORT;

public class EditAccountInfoActivity extends Activity implements View.OnClickListener {
    private User originalUser;

    private EditText etUsername;
    private EditText etPassword;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhoneNumber;
    private EditText etSSN;

    private EditText etStreet;
    private EditText etCity;
    private EditText etState;
    private EditText etZipCode;

    private ImageButton btnRejectChanges;
    private ImageButton btnAcceptChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_info_activity_layout);

        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);

        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etLastName = (EditText)findViewById(R.id.etLastName);
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        etSSN = (EditText)findViewById(R.id.etSSN);

        etStreet = (EditText)findViewById(R.id.etStreet);
        etCity = (EditText)findViewById(R.id.etCity);
        etState = (EditText)findViewById(R.id.etState);
        etZipCode = (EditText)findViewById(R.id.etZipCode);

        btnRejectChanges = (ImageButton)findViewById(R.id.btnRejectChanges);
        btnRejectChanges.setOnClickListener(this);

        btnAcceptChanges = (ImageButton)findViewById(R.id.btnAcceptChanges);
        btnAcceptChanges.setOnClickListener(this);

        originalUser = getIntent().getParcelableExtra(INTENT_USER);
        etUsername.setText(originalUser.getUsername());
        etPassword.setText(originalUser.getPassword());

        etFirstName.setText(originalUser.getCustomer().getFirstName());
        etLastName.setText(originalUser.getCustomer().getLastName());
        etPhoneNumber.setText(originalUser.getCustomer().getPhoneNumber());
        etSSN.setText(originalUser.getCustomer().getSsn());

        etStreet.setText(originalUser.getCustomer().getAddress().getStreet());
        etCity.setText(originalUser.getCustomer().getAddress().getCity());
        etState.setText(originalUser.getCustomer().getAddress().getState());
        etZipCode.setText(originalUser.getCustomer().getAddress().getZipCode());
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etUsername.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etFirstName.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etLastName.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etPhoneNumber.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etSSN.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etStreet.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etCity.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etState.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etZipCode.getWindowToken(), 0);

        switch(v.getId()) {
            case R.id.btnRejectChanges:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnAcceptChanges:
                User newUser = new User(
                    etUsername.getText().toString(),
                    etPassword.getText().toString(),
                    new Customer(
                        originalUser.getCustomer(),
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        new Address(
                            originalUser.getCustomer().getAddress(),
                            etStreet.getText().toString(),
                            etCity.getText().toString(),
                            etState.getText().toString(),
                            etZipCode.getText().toString()
                        ),
                        etPhoneNumber.getText().toString(),
                        etZipCode.getText().toString()
                    )
                );

                updateAccountInfo(newUser);
                break;
        }
    }

    private void updateAccountInfo(final User newUser) {
        //LinearLayout llProgressBar = (LinearLayout)findViewById(R.id.llProgressBar);
        //llProgressBar.setVisibility(View.VISIBLE);
        final ProgressDialog loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.updating_contact_info));
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setCancelable(true);
        loadingDialog.show();

        SharedPreferences preferences = getSharedPreferences(PREFS_PARABANK, MODE_PRIVATE);
        //String protocol = preferences.getString(PREFS_PARABANK_PROTOCOL, getResources().getString(R.string.example_protocol));
        String host = preferences.getString(PREFS_PARABANK_HOST, getResources().getString(R.string.example_host));
        String port = preferences.getString(PREFS_PARABANK_PORT, getResources().getString(R.string.example_port));

        String updateURL = Connection.generateUpdateURL(host, port, Long.toString(newUser.getCustomer().getId()),
                newUser.getCustomer().getFirstName(), newUser.getCustomer().getLastName(),
                newUser.getCustomer().getAddress().getStreet(), newUser.getCustomer().getAddress().getCity(),
                newUser.getCustomer().getAddress().getState(), newUser.getCustomer().getAddress().getZipCode(),
                newUser.getCustomer().getPhoneNumber(), newUser.getCustomer().getSsn(),
                newUser.getUsername(), newUser.getPassword());

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(updateURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                if (!loadingDialog.isShowing()) {
                    return;
                }

                loadingDialog.dismiss();

                Intent i = new Intent();
                i.putExtra(INTENT_USER, newUser);
                setResult(RESULT_OK, i);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                onFailure(statusCode, headers, throwable.getMessage(), throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (statusCode == 200) {
                    onSuccess(statusCode, headers, responseString);
                    return;
                }

                if (!loadingDialog.isShowing()) {
                    return;
                }

                loadingDialog.dismiss();

                AlertDialog.Builder errorDialog = new AlertDialog.Builder(EditAccountInfoActivity.this);
                errorDialog.setTitle(R.string.dialog_update_account_info_failed_title);
                errorDialog.setMessage(String.format("%d - %s", statusCode, responseString.isEmpty() ? throwable.getMessage() : responseString));
                errorDialog.setPositiveButton(R.string.global_action_okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //...
                    }
                });

                errorDialog.show();
            }
        });
    }
}

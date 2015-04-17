package com.parabank.parasoft.app.android;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parabank.parasoft.app.android.adts.Account;
import com.parabank.parasoft.app.android.adts.User;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.parabank.parasoft.app.android.Constants.INTENT_USER;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_HOST;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PORT;

public class MainActivity extends Activity implements View.OnClickListener {
    static final int RESULT_EDIT_ACCOUNT_INFO = 0x000000001;

    private User user;

    private TextView tvFullName;
    private TextView tvAddress;
    private LinearLayout llProgressBar;
    private ImageButton btnLogout;
    private ImageButton btnEditAccountInfo;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        tvFullName = (TextView)findViewById(R.id.tvFullName);
        tvAddress = (TextView)findViewById(R.id.tvAddress);
        llProgressBar = (LinearLayout)findViewById(R.id.llProgressBar);

        btnLogout = (ImageButton)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        btnEditAccountInfo = (ImageButton)findViewById(R.id.btnEditAccountInfo);
        btnEditAccountInfo.setOnClickListener(this);

        loadCustomerInfo();
        loadAccounts();
    }

    private void loadCustomerInfo() {
        user = getIntent().getParcelableExtra(INTENT_USER);
        tvFullName.setText(user.getCustomer().getFullName());
        tvAddress.setText(user.getCustomer().getAddress().getAddress());
    }

    private void loadAccounts() {
        user = getIntent().getParcelableExtra(INTENT_USER);
        SharedPreferences preferences = getSharedPreferences(PREFS_PARABANK, MODE_PRIVATE);
        //String protocol = preferences.getString(PREFS_PARABANK_PROTOCOL, getResources().getString(R.string.example_protocol));
        String host = preferences.getString(PREFS_PARABANK_HOST, getResources().getString(R.string.example_host));
        String port = preferences.getString(PREFS_PARABANK_PORT, getResources().getString(R.string.example_port));

        llProgressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        String accountInfoURL = Connection.generateAccountInfoURL(host, port, Long.toString(user.getCustomer().getId()));
        client.get(accountInfoURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<Account> accounts = new ArrayList<>();

                try {
                    JSONObject obj;
                    JSONArray jsonArray = response.getJSONArray("account");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        obj = jsonArray.getJSONObject(i);
                        accounts.add(new Account(obj));
                    }
                } catch (JSONException e) {
                    AlertDialog.Builder errorDialog = new AlertDialog.Builder(MainActivity.this);
                    errorDialog.setMessage(e.getMessage());
                    errorDialog.setPositiveButton(R.string.global_action_okay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //...
                        }
                    });
                }

                AccountsAdapter adapter = new AccountsAdapter(MainActivity.this, user, accounts);
                ListView lvAccountsList = (ListView)findViewById(R.id.lvQueryResults);
                if (adapter.isEmpty()) {
                    LinearLayout llEmptyList = (LinearLayout)findViewById(R.id.llEmptyList);
                    llEmptyList.setVisibility(View.VISIBLE);
                } else {
                    lvAccountsList.setAdapter(adapter);
                }

                llProgressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogout:
                finish();
                break;
            case R.id.btnEditAccountInfo:
                Intent i = new Intent(this, EditAccountInfoActivity.class);
                i.putExtra(INTENT_USER, getIntent().getParcelableExtra(INTENT_USER));
                startActivityForResult(i, RESULT_EDIT_ACCOUNT_INFO);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_EDIT_ACCOUNT_INFO:
                switch (resultCode) {
                    case RESULT_CANCELED:
                        break;
                    case RESULT_OK:
                        getIntent().putExtra(INTENT_USER, data.getParcelableExtra(INTENT_USER));
                        loadCustomerInfo();
                        break;
                }

                break;
        }
    }
}

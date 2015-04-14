package com.parabank.parasoft.app.android;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parabank.parasoft.app.android.adts.Account;
import com.parabank.parasoft.app.android.adts.Customer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.parabank.parasoft.app.android.Constants.INTENT_CUSTOMER;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_HOST;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PORT;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView tvFullName;
    private TextView tvAddress;

    private LinearLayout llProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        final Customer customer = getIntent().getParcelableExtra(INTENT_CUSTOMER);

        tvFullName = (TextView)findViewById(R.id.tvFullName);
        tvFullName.setText(customer.getFullName());

        tvAddress = (TextView)findViewById(R.id.tvAddress);
        tvAddress.setText(customer.getAddress().getAddress());

        llProgressBar = (LinearLayout)findViewById(R.id.llProgressBar);
        llProgressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = getSharedPreferences(PREFS_PARABANK, MODE_PRIVATE);
        //String protocol = preferences.getString(PREFS_PARABANK_PROTOCOL, getResources().getString(R.string.example_protocol));
        String host = preferences.getString(PREFS_PARABANK_HOST, getResources().getString(R.string.example_host));
        String port = preferences.getString(PREFS_PARABANK_PORT, getResources().getString(R.string.example_port));

        Log.e("parabank", Long.toString(customer.getId()));

        AsyncHttpClient client = new AsyncHttpClient();
        String accountInfoURL = Connection.generateAccountInfoURL(host, port, Long.toString(customer.getId()));
        client.get(accountInfoURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<Account> accounts = new ArrayList<Account>();

                try {
                    JSONObject obj;
                    JSONArray jsonArray = response.getJSONArray("account");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        obj = jsonArray.getJSONObject(i);
                        accounts.add(new Account(obj));
                    }
                } catch (JSONException e) {
                }

                AccountsAdapter adapter = new AccountsAdapter(MainActivity.this, customer, accounts);
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

    @Override
    public void onClick(View v) {
    }
}

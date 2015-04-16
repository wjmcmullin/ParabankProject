package com.parabank.parasoft.app.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_HOST;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PORT;

public class ConnectionSettingsActivity extends Activity implements View.OnClickListener {
    private EditText etHost;
    private EditText etPort;
    private Button btnApply;

    private SharedPreferences preferences;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_settings_activity_layout);

        preferences = getSharedPreferences(PREFS_PARABANK, MODE_PRIVATE);

        etHost = (EditText)findViewById(R.id.etHost);
        etHost.setText(preferences.getString(PREFS_PARABANK_HOST, getResources().getString(R.string.example_host)));
        etHost.selectAll();

        etPort = (EditText)findViewById(R.id.etPort);
        etPort.setText(preferences.getString(PREFS_PARABANK_PORT, getResources().getString(R.string.example_port)));

        btnApply = (Button)findViewById(R.id.btnApply);
        btnApply.setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnApply:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etHost.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etPort.getWindowToken(), 0);
                preferences.edit()
                    .putString(PREFS_PARABANK_HOST, etHost.getText().toString())
                    .putString(PREFS_PARABANK_PORT, etPort.getText().toString())
                    .apply();
                finish();
                break;
        }
    }
}

package com.parabank.parasoft.app.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_HOST;
import static com.parabank.parasoft.app.android.Constants.PREFS_PARABANK_PORT;

public class ConnectionSettingsActivity extends Activity implements View.OnClickListener {
    private EditText etHost;
    private EditText etPort;
    private ImageButton btnAcceptChanges;
    private ImageButton btnRejectChanges;

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

        btnAcceptChanges = (ImageButton)findViewById(R.id.btnAcceptChanges);
        btnAcceptChanges.setOnClickListener(this);

        btnRejectChanges = (ImageButton)findViewById(R.id.btnRejectChanges);
        btnRejectChanges.setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etHost.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etPort.getWindowToken(), 0);
        switch(v.getId()) {
            case R.id.btnAcceptChanges:
                preferences.edit()
                    .putString(PREFS_PARABANK_HOST, etHost.getText().toString())
                    .putString(PREFS_PARABANK_PORT, etPort.getText().toString())
                    .apply();
                finish();
                break;
            case R.id.btnRejectChanges:
                finish();
                break;
        }
    }
}

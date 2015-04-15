package com.parabank.parasoft.app.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parabank.parasoft.app.android.adts.Address;
import com.parabank.parasoft.app.android.adts.Customer;
import com.parabank.parasoft.app.android.adts.User;

import static com.parabank.parasoft.app.android.Constants.INTENT_USER;

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

                Intent i = new Intent();
                i.putExtra(INTENT_USER, newUser);
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }

    private void updateAccountInfo(User newUser) {
        LinearLayout llProgressBar = (LinearLayout)findViewById(R.id.llProgressBar);
        llProgressBar.setVisibility(View.VISIBLE);

    }
}

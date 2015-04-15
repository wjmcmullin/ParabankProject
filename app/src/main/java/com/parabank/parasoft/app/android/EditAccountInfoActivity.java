package com.parabank.parasoft.app.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditAccountInfoActivity extends Activity implements View.OnClickListener {
    private ImageButton btnRejectChanges;
    private ImageButton btnAcceptChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_info_activity_layout);

        btnRejectChanges = (ImageButton)findViewById(R.id.btnRejectChanges);
        btnRejectChanges.setOnClickListener(this);

        btnAcceptChanges = (ImageButton)findViewById(R.id.btnAcceptChanges);
        btnAcceptChanges.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnRejectChanges:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnAcceptChanges:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}

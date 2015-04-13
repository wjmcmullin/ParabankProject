package com.parabank.parasoft.parabankproject;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import com.parabank.parasoft.parabankproject.Connect;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.sample_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.connect_action) {
            new MakeConnection().execute();
            return true;
        }
        if (id == R.id.clear_action){
            TextView results = (TextView) findViewById(R.id.output_text);
            results.setText("");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            //View rootView = inflater.inflate(R.layout.sample_main, container, false);
            return rootView;
        }
    }

    private class MakeConnection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String output = "Empty";
            try {

                Connect connection = new Connect();
                output = connection.makeConnection(getHost(),getPort(),getUsername(),getPassword());

            } catch (Exception e) {
                output = e.getMessage();
            }

            return output;
        }
        @Override
        protected void onPostExecute(String result) {
            TextView results = (TextView) findViewById(R.id.output_text);
            results.setText(result);
        }

    }
    private String getUsername()
    {
        EditText uname = (EditText) findViewById(R.id.user_text);
        return new String(uname.getText().toString());
    }

    private String getPassword()
    {
        EditText pass = (EditText) findViewById(R.id.pass_text);
        return new String(pass.getText().toString());
    }
    private String getHost()
    {
        EditText host = (EditText) findViewById(R.id.url_text);
        return new String(host.getText().toString());
    }

    private String getPort()
    {
        EditText port = (EditText) findViewById(R.id.port_text);
        return new String(port.getText().toString());
    }
}

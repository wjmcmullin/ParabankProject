package com.parabank.parasoft.parabankproject;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


/**
 * Created by bmcmullin on 4/13/15.
 */
public class Connect {

    //Originally returned VOID
    protected String makeConnection(String host, String port, String uname, String password)
    {
        //For the result
        String result = "";
        HttpClient httpClient = new DefaultHttpClient();
        String url = "http://" + host + ":" + port +"/parabank/services/bank/login/" + uname + "/"
                + password + "?_type=json";
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                result = "It worked";
            }
            else
                result = "\n No Connection";
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

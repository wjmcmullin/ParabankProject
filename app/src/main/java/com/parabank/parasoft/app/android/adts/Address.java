package com.parabank.parasoft.app.android.adts;

import org.json.JSONException;
import org.json.JSONObject;

public class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;

    public Address(JSONObject obj) throws JSONException {
        this.street = obj.getString("street");
        this.city = obj.getString("city");
        this.state = obj.getString("state");
        this.zipCode = obj.getString("zipCode");
    }
}

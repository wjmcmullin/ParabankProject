package com.parabank.parasoft.app.android.adts;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final String phoneNumber;
    private final String ssn;

    public Customer(JSONObject obj) throws JSONException {
        this.id = Long.parseLong(obj.getString("id"));
        this.firstName = obj.getString("firstName");
        this.lastName = obj.getString("lastName");
        this.address = new Address(obj.getJSONObject("address"));
        this.phoneNumber = obj.getString("phoneNumber");
        this.ssn = obj.getString("ssn");
    }
}

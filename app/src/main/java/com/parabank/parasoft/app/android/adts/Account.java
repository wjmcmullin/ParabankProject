package com.parabank.parasoft.app.android.adts;

import org.json.JSONException;
import org.json.JSONObject;

public class Account {
    private final long id;
    private final long customerId;
    private final String type;
    private final String balance;

    public Account(JSONObject obj) throws JSONException {
        this.id = Long.parseLong(obj.getString("id"));
        this.customerId = Long.parseLong(obj.getString("customerId"));
        this.type = obj.getString("type");
        this.balance = obj.getString("balance");
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getType() {
        return type;
    }

    public String getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("%d [%s] %s", id, type, balance);
    }
}

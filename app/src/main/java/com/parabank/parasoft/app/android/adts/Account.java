package com.parabank.parasoft.app.android.adts;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents a container for various information about a
 * Parabank account, such as id, type and balance.
 *
 * @author csmith@parasoft.com
 */
public class Account {
    /**
     * ID number for this Account
     */
    private final long id;

    /**
     * ID of the customer who owns this Account.
     */
    private final long customerId;

    /**
     * String representation of the type of this Account.
     * E.g., Checking or Savings.
     */
    private final String type;

    /**
     * String representation of the balance within this account.
     */
    private final String balance;

    /**
     * Constructs an Account using the specified {@link org.json.JSONObject}
     * returned by a response retrieved by a Parabank instance.
     *
     * @param obj JSONObject containing the Account info
     * @throws JSONException when there is an issue parsing the JSONObject,
     *      usually indicates that the JSONObject was not returned within the
     *      response of a Parabank response.
     */
    public Account(JSONObject obj) throws JSONException {
        this.id = Long.parseLong(obj.getString("id"));
        this.customerId = Long.parseLong(obj.getString("customerId"));
        this.type = obj.getString("type");
        this.balance = obj.getString("balance");
    }

    /**
     * Returns the ID number of this Account.
     *
     * @return long representation of the ID number for this Account.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the ID number of the {@link Customer} who owns this Account.
     *
     * @return long representation of the ID number for the Customer
     *      who owns this Account.
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Returns which type of Account this Account is.
     *
     * @return String representation for the type of Account this Account
     *      is. I.e., Checking or Savings.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the current balance of this Account.
     *
     * @return String representation for the Balance of this Account.
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Returns a String representation of this Account in the format of
     * <code>AccountID [ AccountType ] AccountBalance</code>
     *
     * @return String representation of this Account.
     */
    @Override
    public String toString() {
        return String.format("%d [%s] %s", id, type, balance);
    }
}

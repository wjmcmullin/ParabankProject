package com.parabank.parasoft.app.android.adts;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer implements Parcelable {
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

    private Customer(Parcel source) {
        this.id = source.readLong();
        this.firstName = source.readString();
        this.lastName = source.readString();
        this.address = source.readParcelable(Address.class.getClassLoader());
        this.phoneNumber = source.readString();
        this.ssn = source.readString();
    }

    public String getSsn() {
        return ssn;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String toString() {
        return getFullName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeParcelable(address, 0);
        dest.writeString(phoneNumber);
        dest.writeString(ssn);
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}

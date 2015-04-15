package com.parabank.parasoft.app.android.adts;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Address implements Parcelable {
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;

    public Address(Address parent, String street, String city, String state, String zipCode) {
        this.street = street == null ? parent.getStreet() : street;
        this.city = city == null ? parent.getCity() : city;
        this.state = state == null ? parent.getState() : state;
        this.zipCode = zipCode == null ? parent.getZipCode() : zipCode;
    }

    public Address(JSONObject obj) throws JSONException {
        this.street = obj.getString("street");
        this.city = obj.getString("city");
        this.state = obj.getString("state");
        this.zipCode = obj.getString("zipCode");
    }

    public Address(Parcel source) {
        this.street = source.readString();
        this.city = source.readString();
        this.state = source.readString();
        this.zipCode = source.readString();
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddress() {
        return String.format("%s\n%s, %s %s", street, city, state, zipCode);
    }

    @Override
    public String toString() {
        return getAddress();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zipCode);
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}

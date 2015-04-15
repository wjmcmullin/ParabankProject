package com.parabank.parasoft.app.android.adts;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private final String username;
    private final String password;
    private final Customer customer;

    public User(String username, String password, Customer customer) {
        this.username = username;
        this.password = password;
        this.customer = customer;
    }

    private User(Parcel source) {
        this.username = source.readString();
        this.password = source.readString();
        this.customer = source.readParcelable(Customer.class.getClassLoader());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeParcelable(customer, 0);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

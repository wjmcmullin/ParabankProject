package com.parabank.parasoft.app.android.adts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents a container for User data. A User has a
 * username, password and associated {@link Customer} object.
 *
 * @author csmith@parasoft.com
 */
public class User implements Parcelable {
    /**
     * String representation for the username of this User.
     */
    private final String username;

    /**
     * String representation for the password of this User.
     */
    private final String password;

    /**
     * Customer object representing this User.
     */
    private final Customer customer;

    /**
     * Constructs a User with the specified username, password and
     * Customer object.
     *
     * @param username username for this User
     * @param password password for this User
     * @param customer Customer aggregate for this User
     */
    public User(String username, String password, Customer customer) {
        this.username = username;
        this.password = password;
        this.customer = customer;
    }

    /**
     * Constructs a User using the specified {@link Parcel}.
     *
     * @param source Parcel containing the information for the User.
     */
    private User(Parcel source) {
        this.username = source.readString();
        this.password = source.readString();
        this.customer = source.readParcelable(Customer.class.getClassLoader());
    }

    /**
     * Returns the username of this User.
     *
     * @return String representation of the username of this User.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of this User.
     *
     * @return String representation of the password of this User.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the Customer aggregate associated with this User.
     *
     * @return Customer aggregate associated with this User.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeParcelable(customer, 0);
    }

    /**
     * Class used to help create a {@link Parcel} for a User.
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        /**
         * {@inheritDoc}
         */
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        /**
         * {@inheritDoc}
         */
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

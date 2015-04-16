package com.parabank.parasoft.app.android.adts;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents a container for the Address aggregate of a
 * {@link Customer}. An Address has a street, city, state and zip code.
 *
 * @author csmith@parasoft.com
 */
public class Address implements Parcelable {
    /**
     * String representation for the street address of this Address.
     */
    private final String street;

    /**
     * String representation for the city of this Address.
     */
    private final String city;

    /**
     * String representation for the state of this Address.
     */
    private final String state;

    /**
     * String representation for the zip code of this Address.
     */
    private final String zipCode;

    /**
     * Constructs an Address using the values from the specified parent
     * Address when a field is null. I.e., if street is <code>null</code>,
     * then the constructed object will use the street of the parent.
     *
     * @param parent parent Address to construct this Address from
     * @param street street of this Address, or <code>null</code> to use
     *      the parent Address's street
     * @param city city of this Address, or <code>null</code> to use
     *      the parent Address's city
     * @param state state of this Address, or <code>null</code> to use
     *      the parent Address's state
     * @param zipCode zipCode of this Address, or <code>null</code> to use
     *      the parent Address's zipCode
     */
    public Address(Address parent, String street, String city, String state, String zipCode) {
        this.street = street == null ? parent.getStreet() : street;
        this.city = city == null ? parent.getCity() : city;
        this.state = state == null ? parent.getState() : state;
        this.zipCode = zipCode == null ? parent.getZipCode() : zipCode;
    }

    /**
     * Constructs an Address using the values contained within the
     * specified {@link org.json.JSONObject}.
     *
     * @param obj JSONObject containing the Address information.
     * @throws JSONException when there is an issue parsing the specified
     *      JSONObject, usually when the specified JSONObject is not one
     *      which was returned within the response from a Parabank
     *      request.
     */
    public Address(JSONObject obj) throws JSONException {
        this.street = obj.getString("street");
        this.city = obj.getString("city");
        this.state = obj.getString("state");
        this.zipCode = obj.getString("zipCode");
    }

    /**
     * Constructs an Address using the specified {@link Parcel}.
     *
     * @param source Parcel containing the information for the Address.
     */
    private Address(Parcel source) {
        this.street = source.readString();
        this.city = source.readString();
        this.state = source.readString();
        this.zipCode = source.readString();
    }

    /**
     * Returns the street of this Address.
     *
     * @return String representation of the street of this Address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Returns the city of this Address.
     *
     * @return String representation of the city of this Address.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the state of this Address.
     *
     * @return String representation of the state of this Address.
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the zip code of this Address.
     *
     * @return String representation of the zip code of this Address.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Returns the concatenation of the individual fields of this Address.
     * Formatted as: <code>street\ncity, state zipCode</code>
     *
     * @return String representation of this Address.
     */
    public String getAddress() {
        return String.format("%s\n%s, %s %s", street, city, state, zipCode);
    }

    /**
     * Returns a String representation of this Address formatted as
     * specified within {@link #getAddress()}.
     *
     * @return String representation of this Address.
     */
    @Override
    public String toString() {
        return getAddress();
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
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zipCode);
    }

    /**
     * Class used to help create a {@link Parcel} for an Address.
     */
    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}

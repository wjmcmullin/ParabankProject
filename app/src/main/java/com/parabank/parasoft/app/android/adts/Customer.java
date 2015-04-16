package com.parabank.parasoft.app.android.adts;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents a container for the Customer aggregate of a
 * {@link User}. A Customer has an ID, name, {@link Address}, phone
 * number and social security number.
 *
 * @author csmith@parasoft.com
 */
public class Customer implements Parcelable {
    /**
     * ID number for this Customer.
     */
    private final long id;

    /**
     * String representation for the first name of this Customer.
     */
    private final String firstName;

    /**
     * String representation for the last name of this Customer.
     */
    private final String lastName;

    /**
     * Aggregate storing the address of this Customer.
     */
    private final Address address;

    /**
     * String representation for the phone number of this Customer.
     */
    private final String phoneNumber;

    /**
     * String representation for the social security number of this
     * Customer.
     */
    private final String ssn;

    /**
     * Constructs a Customer using the values from the specified parent
     * Customer when a field is null. I.e., if firstName is <code>null</code>,
     * then the constructed object will use the firstName of the parent.
     *
     * @param parent parent Customer to construct this Customer from
     * @param firstName first name of this Customer
     * @param lastName last name of this Customer
     * @param address address of this Customer
     * @param phoneNumber phone number of this Customer
     * @param ssn social security number of this Customer
     */
    public Customer(Customer parent, String firstName, String lastName, Address address, String phoneNumber, String ssn) {
        this.id = parent.getId();
        this.firstName = firstName == null ? parent.getFirstName() : firstName;
        this.lastName = lastName == null ? parent.getLastName() : lastName;
        this.address = address == null ? parent.getAddress() : address;
        this.phoneNumber = phoneNumber == null ? parent.getPhoneNumber() : phoneNumber;
        this.ssn = ssn == null ? parent.getSsn() : ssn;
    }

    /**
     * Constructs a Custpmer using the values contained within the
     * specified {@link org.json.JSONObject}.
     *
     * @param obj JSONObject containing the Customer information.
     * @throws JSONException when there is an issue parsing the specified
     *      JSONObject, usually when the specified JSONObject is not one
     *      which was returned within the response from a Parabank
     *      request.
     */
    public Customer(JSONObject obj) throws JSONException {
        this.id = Long.parseLong(obj.getString("id"));
        this.firstName = obj.getString("firstName");
        this.lastName = obj.getString("lastName");
        this.address = new Address(obj.getJSONObject("address"));
        this.phoneNumber = obj.getString("phoneNumber");
        this.ssn = obj.getString("ssn");
    }

    /**
     * Constructs a Customer using the specified {@link Parcel}.
     *
     * @param source Parcel containing the information for the Customer.
     */
    private Customer(Parcel source) {
        this.id = source.readLong();
        this.firstName = source.readString();
        this.lastName = source.readString();
        this.address = source.readParcelable(Address.class.getClassLoader());
        this.phoneNumber = source.readString();
        this.ssn = source.readString();
    }

    /**
     * Returns the social security number of this Customer.
     *
     * @return String representation of the social security number of
     *      this Customer.
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Returns the ID number of this Customer.
     *
     * @return long representation of the ID number of this Customer.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the first name of this Customer.
     *
     * @return String representation of the first name of this Customer.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of this Customer.
     *
     * @return String representation of the last name of this Customer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the Address object containing the address of this Customer.
     *
     * @return Address representation of the address of this Customer.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns the phone number of this Customer.
     *
     * @return String representation of the phone number of this Customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the concatenation of the first and last names of this
     * Customer.
     *
     * @return the full name of this Customer.
     */
    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    /**
     * Returns a String representation of this Customer formatted as
     * specified within {@link #getFullName()}.
     *
     * @return String representation of this Customer.
     */
    @Override
    public String toString() {
        return getFullName();
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
        dest.writeLong(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeParcelable(address, 0);
        dest.writeString(phoneNumber);
        dest.writeString(ssn);
    }

    /**
     * Class used to help create a {@link Parcel} for a Customer.
     */
    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        /**
         * {@inheritDoc}
         */
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        /**
         * {@inheritDoc}
         */
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}

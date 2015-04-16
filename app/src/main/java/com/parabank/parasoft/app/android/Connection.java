package com.parabank.parasoft.app.android;

/**
 * This class contains static methods for generating various URLs used within
 * the HTTP requests of this app.
 *
 * @author jrodriguez@parasoft.com
 */
public final class Connection {
    /**
     * This class is uninitializable.
     */
    private Connection() {
        //...
    }

    /**
     * Generates and returns a String representation of the URL to the
     * specified Parabank instance which will return the account information
     * for the given account number.
     *
     * @param host host to sent the HTTP request to
     * @param port port on the host for Parabank
     * @param accountNum account number whose info to retrieve
     * @return a String representation of the URL to use in order to retrieve
     *      the account information for the given account
     */
    public static String generateAccountInfoURL(String host, String port, String accountNum) {
        String url = "http://" + host + ":" + port + "/parabank/services/bank/customers/"
                + accountNum + "/accounts?_type=json";

        return url.replaceAll("\\s", "%20");
    }

    /**
     * Generates and returns a String representation of the URL to the
     * specified Parabank instance which will determine whether or not the
     * specified username/password combination are valid.
     *
     * @param host host to sent the HTTP request to
     * @param port port on the host for Parabank
     * @param uname Parabank username to attempt login with
     * @param password corresponding password for the specified username
     * @return a String representation of the URL to use in order to
     *      determine whether or not the specified username/password
     *      combination are valid.
     */
    public static String generateLoginURL(String host, String port, String uname, String password) {
        String url = "http://" + host + ":" + port + "/parabank/services/bank/login/"
                + uname + "/" + password + "?_type=json";

        return url.replaceAll("\\s", "%20");
    }

    /**
     * Generates and returns a String representation of the URL to the
     * specified Parabank instance which can be used to sent a POST
     * HTTP request and update the given account number within that
     * Parabank instance the specified information.
     *
     * @param host host to sent the HTTP request to
     * @param port port on the host for Parabank
     * @param accountNumber account number within the Parabank instance
     *      to update
     * @param firstName new first name for that account number
     * @param lastName new last name for that account number
     * @param address new address for that account number
     * @param city new city for that account number
     * @param state new state for that account number
     * @param zipCode new zip code for that account number
     * @param phoneNumber new phone number for that account number
     * @param socialSecurity new social security number for that account
     *      number
     * @param uname new username for that account number
     * @param password new password for that account number
     * @return a String representation of the URL to use in send a POST HTTP
     *      request which will update the specifeid account number with this
     *      new information.
     */
    public static String generateUpdateURL(String host, String port, String accountNumber,
                                           String firstName, String lastName, String address, String city, String state,
                                           String zipCode, String phoneNumber, String socialSecurity, String uname,
                                           String password) {

        String url = "http://" + host + ":" + port + "/parabank/services/bank/customers/update/" + accountNumber + "/"
                + firstName + "/" + lastName + "/" + address + "/" + city + "/" + state + "/"
                + zipCode + "/" + phoneNumber + "/" + socialSecurity + "/" + uname + "/" + password
                + "?_type=json";

        return url.replaceAll("\\s", "%20");
    }
}

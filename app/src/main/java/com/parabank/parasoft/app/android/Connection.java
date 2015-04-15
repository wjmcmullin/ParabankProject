package com.parabank.parasoft.app.android;

import android.util.Log;

public final class Connection {
    public static String generateAccountInfoURL (String host, String port, String accountNumber) {

        String url = "http://" + host + ":" + port + "/parabank/services/bank/customers/"
                + accountNumber + "/accounts?_type=json";

        return url.replaceAll("\\s", "%20");
    }

    public static String generateLoginURL (String host, String port, String uname, String password)
    {
        String url = "http://" + host + ":" + port + "/parabank/services/bank/login/"
                + uname + "/" + password + "?_type=json";

        return url.replaceAll("\\s", "%20");
    }

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

    public static  String generateUpdateURL (String host, String port, String accountNumber,
                String firstName, String lastName, String address, String city, String state,
                String zipCode, String phoneNumber, String socialSecurity, String uname,
                String password) {

        String url = "http://" + host + ":" + port + "customers/update/" + accountNumber + "/" +
                firstName + "/" + lastName + "/" + address + "/" + city + "/" + state + "/" +
                zipCode + "/" + phoneNumber + "/" + socialSecurity + "/" + uname + "/" + password +
                "?_type=json";

        return url;

    }
}

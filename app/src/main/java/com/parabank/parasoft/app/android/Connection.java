package com.parabank.parasoft.app.android;

public final class Connection {
    public static String generateAccountInfoURL(String host, String port, String accountNum) {
        String url = "http://" + host + ":" + port + "/parabank/services/bank/customers/"
                + accountNum + "/accounts?_type=json";

        return url;
    }

    public static String generateLoginURL(String host, String port, String uname, String password) {
        String url = "http://" + host + ":" + port + "/parabank/services/bank/login/"
                + uname + "/" + password + "?_type=json";

        return url;
    }
}

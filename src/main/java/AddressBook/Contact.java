package AddressBook;

import AddressBook.json.JSONException;
import AddressBook.json.JSONObject;

public class Contact {

    private String customerID;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String address;
    private String city;
    private String email;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;

    private String test;

    public Contact(JSONObject contact) throws JSONException {
        this.companyName = contact.getString("CompanyName");
        this.email = contact.getString("Email");
        this.address = contact.getString("Address");
        this.phone = contact.get("Phone").toString();
        this.postalCode = contact.get("PostalCode").toString();
        this.customerID = contact.getString("CustomerID");
        this.city = contact.getString("City");
        this.fax = contact.get("Fax").toString();
        this.contactName = contact.getString("ContactName");
        this.contactTitle = contact.getString("ContactTitle");
    }

}

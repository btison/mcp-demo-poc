package org.globex.mcp.store.service.model;

public class Customer {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Address address;

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

}

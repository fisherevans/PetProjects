package com.fisherevans.webservutil.json;

/**
 * Author: Fisher Evans
 * Date: 7/21/14
 */
public class User extends JSONObject {
    private String _username, _firstName, _lastName, _email;
    private DateOfBirth _dob;

    public User(String username, String firstName, String lastName, String email, DateOfBirth dob) {
        _username = username;
        _firstName = firstName;
        _lastName = lastName;
        _email = email;
        _dob = dob;
    }

    @JSONField(name = "username", desc = "Username")
    public String getUsername() {
        return _username;
    }

    public void setUsername(String username) {
        _username = username;
    }

    @JSONField(name = "firstName", desc = "First Name")
    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    @JSONField(name = "lastName", desc = "Last Name")
    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }

    @JSONField(name = "email", desc = "Email Address")
    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        _email = email;
    }

    @JSONField(name = "dob", desc = "Date of Birth")
    public DateOfBirth getDob() {
        return _dob;
    }

    public void setDob(DateOfBirth dob) {
        _dob = dob;
    }
}

package model;

public class ChangeEmployeeInfo {

    private final String lastName;
    private final String email;
    private final String url;
    private final String phone;
    private final Boolean isActive;

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public ChangeEmployeeInfo(String lastName, String email, String url, String phone, Boolean isActive) {
        this.lastName = lastName;
        this.email = email;
        this.url = url;
        this.phone = phone;
        this.isActive = isActive;
    }
}

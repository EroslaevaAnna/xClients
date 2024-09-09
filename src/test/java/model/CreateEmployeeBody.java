package model;

public class CreateEmployeeBody {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final Integer companyId;
    private final String email;
    private final String url;
    private final String phone;
    private final String birthdate;
    private final Boolean isActive;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Integer getCompanyId() {
        return companyId;
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

    public String getBirthdate() {
        return birthdate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public CreateEmployeeBody(Integer id, String firstName, String lastName, String middleName, Integer companyId, String email, String url, String phone, String birthdate, Boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.companyId = companyId;
        this.email = email;
        this.url = url;
        this.phone = phone;
        this.birthdate = birthdate;
        this.isActive = isActive;
    }
}

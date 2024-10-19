package service;

import com.github.javafaker.Faker;
import gata.EmployeeData;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import model.Auth;
import model.ChangeEmployeeInfo;
import model.CreateCompany;
import model.CreateEmployeeBody;
import tests.ContractEmployeeTest;

import java.time.LocalDate;

import static gata.EmployeeData.companyId;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static tests.ContractEmployeeTest.idEmployee;
import static tests.ContractEmployeeTest.token;

public class ApiService {


    private Integer idCompany;

    //получить токен
    public static String getToken() {
        String username = ConfHelper.getProperty("username");
        String password = ConfHelper.getProperty("password");
        Auth auth = new Auth(username, password);

        return given().header("accept", "application/json")
                .header("content-type","application/json")
                .body(auth)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(201)
                .extract().response().jsonPath().getString("userToken");
    }

    //добавить новую компанию
    public static String getCompany() {
        final Faker faker = new Faker();
        String name = faker.name().lastName();
        String description = faker.lorem().sentence();
        CreateCompany companybody = new CreateCompany(name, description);


        return given()
                .header("x-client-token", token)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .body(companybody)
                .when()
                .post("/company")
                .then()
                .statusCode(201)
                .extract().response().jsonPath().getString("id");

    }

    //содать сотрудника
    public static String getEmployee() {
        int id = EmployeeData.id;
        String firstName = EmployeeData.firstName;
        String lastName = EmployeeData.lastName;
        String middleName = EmployeeData.middleName;
        Integer companyId = EmployeeData.companyId;
        String email = EmployeeData.email;
        String url = EmployeeData.url;
        String phone = EmployeeData.phone;
        String birthdate = EmployeeData.birthdate;
        Boolean isActive = EmployeeData.isActive;
        CreateEmployeeBody employeeBody;
        employeeBody = new CreateEmployeeBody(id, firstName, lastName, middleName, companyId, email, url, phone, birthdate, isActive);

        return given()

                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("x-client-token", token)
                .body(employeeBody)
                .when()
                .post("/employee")
                .then()
                .statusCode(201)
                .extract().response().jsonPath().getString("id");
    }

     //получить инфо по сотруднику (id)
    public static void printEmployeeInfo(int id) {

        given()
                .basePath("employee")
                .when()
                .get("{employeeId}", id)
                .then()
                .statusCode(200);

    }

    public static class Employee {
        public int id;
        public String firstName;
        public String lastName;
        public LocalDate birthdate;
        public boolean isActive;

        public Employee(int id, String firstName, String lastName, LocalDate birthdate, boolean isActive) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthdate = birthdate;
            this.isActive = isActive;
        }
    }

    //изменить сотруднику данные
    public static String ChangeEmployee() {
        final Faker faker = new Faker();

        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String url = faker.internet().url();
        String phone = faker.phoneNumber().cellPhone();
        Boolean isActive = faker.bool().bool();
        ChangeEmployeeInfo employeeInfo = new ChangeEmployeeInfo(lastName, email, url, phone, isActive);


        return given()

                .basePath("employee")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("x-client-token", token)
                .body(employeeInfo)
                .when()
                .patch("{employeeId}", idEmployee)
                .then()
                .statusCode(200)
                .body("id", equalTo(idEmployee))
                .extract().response().jsonPath().getString("id");
    }

    public static Response getEmployeeInfo(int idEmployee) {

        return given()
                .accept(ContentType.JSON)
                .when()
                .get("/employee/" + idEmployee)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
    public Object getListEmployee() {

    idCompany = Integer.valueOf(ApiService.getCompany());
    idEmployee = Integer.valueOf(ApiService.getEmployee());

    return given()
                .header("Accept", "application/json")
                .when()
                .request(Method.GET, "https://x-clients-be.onrender.com/employee?company=" +companyId)
                .then()
                .statusCode(200)
                .body("", hasSize(greaterThanOrEqualTo(1)))
            .extract().response().jsonPath().getString("id");

    }

    public static void deleteCompany(String token, Integer companyId) {
        given()
                .basePath("company/delete/" + EmployeeData.companyId)
                .header("x-client-token", ContractEmployeeTest.token)
                .contentType("application/json")
                .when()
                .get();
    }

}

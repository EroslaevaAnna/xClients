package service;

import com.github.javafaker.Faker;
import gata.EmployeeData;
import model.Auth;
import model.ChangeEmployeeInfo;
import model.CreateCompany;
import model.CreateEmployeeBody;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.ContractEmployeeTest.*;

public class ApiService {


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

                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("x-client-token", token)
                .body(companybody)
                .when()
                .post("/company")
                .then()
                .statusCode(201)
                .extract().response().jsonPath().getString("id");

    }

    //содать сотрудника
        public static String getEmployee() {
            Integer id = EmployeeData.id;
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
            employeeBody = new CreateEmployeeBody(id,firstName,lastName,middleName,companyId, email, url, phone, birthdate, isActive);

            return given()

                    .header("accept", "application/json")
                    .header("content-type","application/json")
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
                .body().prettyPrint();
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

//    public static void deleteCompany() {
//        given()
//                .basePath("company/delete/" + idCompany)
//                .header("x-client-token", token)
//                .contentType("application/json")
//                .when()
//                .get();
//    }

}

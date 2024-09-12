package service;

import gata.EmployeeData;
import model.Auth;
import model.ChangeEmployeeInfo;
import model.CreateEmployeeBody;

import static io.restassured.RestAssured.given;
import static tests.ContractEmployeeTest.idEmployee;
import static tests.ContractEmployeeTest.token;

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
            CreateEmployeeBody employeeBody = new CreateEmployeeBody(id,firstName,lastName,middleName,companyId, email, url, phone, birthdate, isActive);


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

        //создать сотрудника

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

        String lastName = ConfHelper.getProperty("lastNamePatch");
        String email = ConfHelper.getProperty("emailPatch");
        String url = ConfHelper.getProperty("urlPatch");
        String phone = ConfHelper.getProperty("phonePatch");
        Boolean isActive = Boolean.valueOf(ConfHelper.getProperty("isActivePatch"));
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
                .extract().response().jsonPath().getString("id");

    }


}

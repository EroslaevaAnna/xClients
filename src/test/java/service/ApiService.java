package service;

import model.Auth;
import model.CreateEmployeeBody;

import static io.restassured.RestAssured.given;

public class ApiService {
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


        public static String getEmployee() {
            Integer id = Integer.valueOf(ConfHelper.getProperty1("id"));
            String firstName = ConfHelper.getProperty("firstName");
            String lastName = ConfHelper.getProperty("lastName");
            String middleName = ConfHelper.getProperty("middleName");
            Integer companyId = Integer.valueOf(ConfHelper.getProperty("companyId"));
            String email = ConfHelper.getProperty("email");
            String url = ConfHelper.getProperty("url");
            String phone = ConfHelper.getProperty("phone");
            String birthdate = ConfHelper.getProperty("birthdate");
            Boolean isActive = Boolean.valueOf(ConfHelper.getProperty("isActive"));
            CreateEmployeeBody employeeBody = new CreateEmployeeBody(id,firstName,lastName,middleName,companyId, email, url, phone, birthdate, isActive);


            return given()

                    .header("accept", "application/json")
                    .header("content-type","application/json")
                    .body(employeeBody)
                    .when()
                    .post("/emplooyee")
                    .then()
                    .statusCode(201)
                    .extract().response().jsonPath().getString("id");
        }

}

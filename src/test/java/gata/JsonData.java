package gata;

import model.CreateEmployeeBody;

import static io.restassured.RestAssured.given;
import static tests.EmployeeTest.idCompany;
import static tests.EmployeeTest.token;

public class JsonData {
    //содать сотрудника для проверки возврата ожидаемого json
    public static String getEmployee() {
        int id = 0;
        String firstName = "Петр";
        String lastName = "Петров";
        String middleName = "Петрович";
        Integer companyId = idCompany;
        String email = "email123@mail.ru";
        String url = "url";
        String phone = "123456";
        String birthdate = "2024-10-19T10:44:31.631Z";
        Boolean isActive = true;
        CreateEmployeeBody employeeBodyJson;
        employeeBodyJson = new CreateEmployeeBody(id, firstName, lastName, middleName, companyId, email, url, phone, birthdate, isActive);

        return given()

                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("x-client-token", token)
                .body(employeeBodyJson)
                .when()
                .post("/employee")
                .then()
                .statusCode(201)
                .extract().response().jsonPath().getString("id");
    }
}

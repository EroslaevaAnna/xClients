package tests;

import DB.DatabaseEmployeeCheck;
import gata.JsonData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import service.ApiService;
import service.ConfHelper;
import java.sql.SQLException;
import static gata.EmployeeData.companyId;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static service.ApiService.getEmployee;


public class EmployeeTest {
    public static String token;
    public static Integer idEmployee;
    public static Integer idCompany;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = ConfHelper.getProperty("base_uri");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        token = ApiService.getToken();
    }

    @Test
    @DisplayName("Получить токен - авторизоваться")
    @Tag("Позитивный")
    public void authToken() {

        System.out.println(token);
    }

    @Test
    @DisplayName("Добавить новую компанию")
    @Tag("Позитивный")
    @Tag("business_test")
    public void iCanCreateNewCompany() {

        idCompany = Integer.valueOf(ApiService.getCompany());
        System.out.println(idCompany);
    }

    @Test
    @DisplayName("Добавить нового сотрутдника проверить в БД")
    @Tag("Позитивный")
    @Tag("business_test")
    public void addNewEmployeeToCheckInDatabase() throws SQLException {
        idCompany = Integer.valueOf(ApiService.getCompany());
        idEmployee = Integer.valueOf(ApiService.getEmployee());
        boolean isEmployeeInDb = DatabaseEmployeeCheck.EmployeeInDB(idEmployee);
        assertTrue(isEmployeeInDb);
    }

    @Test
    @DisplayName("Добавить нового сотрутдника")
    @Tag("Позитивный")
    @Tag("business_test")
    public void iCanCreateNewEmployee() {
        idEmployee = Integer.valueOf(getEmployee());
        System.out.println(idEmployee);
    }

    @Test
    @DisplayName("Получить сотрудника по id")
    @Tag("Позитивный")
    @Tag("business_test")
    public void iCanGetEmployeeById() {
        idEmployee = Integer.parseInt(getEmployee());
        ApiService.printEmployeeInfo(idEmployee);
    }

    @Test
    @DisplayName("Проверяет, что метод возвращает статус отличный от 200 при неверном ID сотрудника")
    @Tag("Негативный")
    @Tag("contract_test")
    public void invalidEmployeeInfoRetrieval() {
        int invalidEmployeeId = -1;
        when()
                .get("{employeeId}", invalidEmployeeId)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Изменить информацию по сотруднику") // ошибка, не изменился номер телефона
    @Tag("Позитивный")
    @Tag("business_test")
    public void iCanChangeEmployeeInformation() {
        idEmployee = Integer.valueOf(getEmployee());
        Response response = ApiService.getEmployeeInfo(idEmployee);
        String lastName = response.jsonPath().getString("lastName");
        String email = response.jsonPath().getString("email");
        String phone = response.jsonPath().getString("phone");
        String url = response.jsonPath().getString("url");

        int ChangeInfo = Integer.parseInt(String.valueOf(ApiService.ChangeEmployee()));
        ApiService.printEmployeeInfo(ChangeInfo);
        Response response1 = ApiService.getEmployeeInfo(idEmployee);
        String editedLastName = response1.jsonPath().getString("lastName");
        String editedEmail = response1.jsonPath().getString("email");
        String editedPhone = response1.jsonPath().getString("phone");
        String editedUrl = response1.jsonPath().getString("url");

        assertNotEquals(editedLastName, lastName);
        assertNotEquals(editedEmail, email);
        assertNotEquals(editedPhone, phone);
        assertNotEquals(editedUrl, url);
    }

    @Test
    @DisplayName("Получить список сотрудников для компании -статус 200, ответ не null")
    @Tag("Позитивный")
    @Tag("contract_test")
    public void iCanGetListEmployeesByCompanyId() {
        Object response = new ApiService().getListEmployee();
        assertThat(response.hashCode() == 200);
        assertNotNull(response);
    }

    @Test
    @DisplayName("отсутствие сотрудников у несуществующей компании")
    @Tag("Негативный")
    @Tag("business_test")
    public void negativeTestNoEmployees() {
        int nonExistentCompanyId = 9999;

        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/employee?company=" + nonExistentCompanyId)
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.body().asString();
        assertTrue(responseBody.equals("[]"));
    }

    @Test
    @DisplayName("Запрашиваем список сотрудников пустой компании")
    @Tag("business_test")
    public void emptyCompanyList() {
        int companyId = Integer.valueOf(ApiService.getCompany());

        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/employee?company=" + companyId)
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.body().asString();
        assertTrue(responseBody.equals("[]"));
    }

    @Test
    @DisplayName("проверяет, что при получении сотрудника по ID возвращается ожидаемый JSON")
    @Tag("contract_test")
    // ошибка, email возврвщает null
    public void shouldGetEmployeeDetails() {
        idCompany = Integer.valueOf(ApiService.getCompany());
        System.out.println(idCompany);
        int idEmployeeJson = Integer.parseInt(JsonData.getEmployee());

        given().basePath("employee").
                when().get("{employeeId}", idEmployeeJson).
                then().statusCode(200).
                body("id", is(idEmployeeJson)).
                body("firstName", is("Петр")).
                body("lastName", is("Петров")).
                body("middleName", is("Петрович")).
                body("companyId", is(idCompany)).
                body("email", is("email123@mail.ru")).
                body("url", is("url")).
                body("phone", is("123456")).
                body("birthdate", is("2024-10-19")).
                body("isActive", is(true));
    }

    @AfterAll
    public static void tearDown() {

        ApiService.deleteCompany(token, companyId);

    }
}
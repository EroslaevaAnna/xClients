package tests;

import DB.DatabaseEmployeeCheck;
import com.github.javafaker.Faker;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static service.ApiService.getEmployee;


public class ContractEmployeeTest {

    private final Faker faker = new Faker();
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
    @DisplayName("Добавить новую компанию - статус код 201, присвоен id")
    @Tag("Позитивный")
    public void iCanCreateNewCompany() {

        idCompany = Integer.valueOf(ApiService.getCompany());
        System.out.println(idCompany);
    }
    @Test
    @DisplayName("Добавить нового сотрутдника проверить в БД")
    @Tag("Позитивный")
    public void addNewEmployeeToCheckInDatabase() throws SQLException {
        idCompany = Integer.valueOf(ApiService.getCompany());
        idEmployee = Integer.valueOf(ApiService.getEmployee());
        boolean isEmployeeInDb = DatabaseEmployeeCheck.EmployeeInDB(idEmployee);
        assertTrue(isEmployeeInDb);
    }

    @Test
    @DisplayName("Добавить нового сотрутдника - статус код 201, присвоен id")
    @Tag("Позитивный")
    public void iCanCreateNewEmployee() {

        idEmployee = Integer.valueOf(getEmployee());
        System.out.println(idEmployee);
    }

    @Test
    @DisplayName("Получить сотрудника по id- статус код 200")
    @Tag("Позитивный")
    public void iCanGetEmployeeById() {
        idEmployee = Integer.parseInt(getEmployee());
        ApiService.printEmployeeInfo(idEmployee);
    }

    @Test
    @DisplayName("Проверяет, что метод возвращает статус отличный от 200 при неверном ID сотрудника")
    @Tag("Негативный")
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
    @DisplayName("Получить список сотрудников для компании - статус 200, в теле хотя бы один ответ")
    @Tag("Позитивный")
    public void iCanGetListEmployeesByCompanyId() {
        Object listEmloyee = new ApiService().getListEmployee();
        System.out.println(listEmloyee);
    }
    @Test
    @DisplayName("Запрашиваем список сотрудников пустой компании")
    public void emptyCompanyList() {
        idCompany = Integer.valueOf(ApiService.getCompany());
        Object listEmloyee = new ApiService().getListEmployee();
    }
    @Test
    @DisplayName("проверяет, что при получении сотрудника по ID возвращается ожидаемый JSON")
    // ошибка, email возврвщает null
    public void shouldGetEmployeeDetails() {
        idCompany = Integer.valueOf(ApiService.getCompany());
        System.out.println(idCompany);
        int idEmployeeJson = Integer.parseInt(JsonData.getEmployee());

        given().basePath("employee").
                when().get("{employeeId}", idEmployeeJson).
                then().statusCode(200).
                body("id", is(0)).
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

package tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import service.ApiService;
import service.ConfHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ContractEmployeeTest {

    private final Faker faker = new Faker();
    public static String token;
    public static Integer idEmployee;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = ConfHelper.getProperty("base_uri");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // получить токен- взять логин пароль из файла проперти, взять тело запроса в виде json, отформатировать, заголовки все это оформит в отдельный класс помошник
        token = ApiService.getToken();
    }

    @Test
    @DisplayName("Получить токен - авторизоваться")
    @Tag("Позитивный")
    public void authToken() {

        System.out.println(token);
    }

    @Test
    @DisplayName("Добавить нового сотрутдника - статус код 201, присвоен id")
    @Tag("Позитивный")
    public void iCanCreateNewEmployee() {

        idEmployee = Integer.valueOf(ApiService.getEmployee());
        System.out.println(idEmployee);
    }

    @Test
    @DisplayName("Создание сотрудника с невалидным токеном")
    @Tag("Негативный")
    public void iCannotCreateEmployeeWithInvalidToken() {

        given().header("content-type", "application/json")
                .header("x-client-token", "InvalidToken")
                .accept("application/json")
                .body()
    }

    @Test
    @DisplayName("Получить сотрудника по id")
    @Tag("Позитивный")
    public void iCanGetEmployeeById() {
        idEmployee = Integer.parseInt(ApiService.getEmployee());
        ApiService.printEmployeeInfo(idEmployee);
    }

    @Test
    @DisplayName("Изменить информацию по сотруднику")
    @Tag("Позитивный")
    public void iCanChangeEmployeeInformation() {
        idEmployee = Integer.valueOf(ApiService.getEmployee());

        int ChangeInfo = Integer.parseInt(String.valueOf(ApiService.ChangeEmployee()));
        ApiService.printEmployeeInfo(ChangeInfo);
    }

    @Test
    @DisplayName("Получить список сотрудников для компании - статус 200, в теле хотя бы один ответ")
    @Tag("Позитивный")
    public void iCanGetListEmployeesByCompanyId() {
        given()
                .header("Accept", "application/json")
                .when()
                .request(Method.GET, "https://x-clients-be.onrender.com/employee?company=12")
                .then()
                .statusCode(200)
                .body("", hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("Проверить не получение списока сотрудников по существующей компании -статус код 200, в теле пустой массив")
    @Tag("Негативный")
    public void iCanNotGetListEmployeesForNonExistentCompany() {
        given()
                .header("Accept", "application/json")
                .when()
                .request(Method.GET, "https://x-clients-be.onrender.com/employee?company=98798798")
                .then()
                .statusCode(200)
                .body("", empty());
    }


}

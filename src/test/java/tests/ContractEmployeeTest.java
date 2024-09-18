package tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import model.CreateEmployeeBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import service.ApiService;
import service.ConfHelper;

import static gata.EmployeeData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


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
        CreateEmployeeBody employeeBody = new CreateEmployeeBody(id,firstName,lastName,middleName,companyId, email, url, phone, birthdate, isActive);

       given().header("content-type", "application/json")
                .header("x-client-token", "InvalidToken")
                .accept("application/json")
                .body(employeeBody)
                .when()
                .post("/employee")
                .then()
                .statusCode(401);
    }

//    @Test
//    @DisplayName("Создать 2 сотрудника и п олучить в списке 2 сотрудника по id")
//    @Tag("Позитивный")
//    public void iCanGet2EmployeeById() {
//
//        int idEmployee1  = Integer.parseInt(ApiService.getEmployee());
//        int idEmployee1 = new ApiService().get
//        ApiService.printEmployeeInfo(idEmployee);
//    }

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

//        Object employeeBody = new Object();
//        assertThat(employeeBody, not(equalTo(ChangeInfo))); // как сравнить часть параметров тела в этой строке стравнивается только id
//        System.out.println(ChangeInfo);
//        System.out.println(employeeBody);
    }

    @Test
    @DisplayName("Получить список сотрудников для компании - статус 200, в теле хотя бы один ответ")
    @Tag("Позитивный")
    public void iCanGetListEmployeesByCompanyId() {
        idCompany = Integer.valueOf(ApiService.getCompany());
        idEmployee = Integer.valueOf(ApiService.getEmployee());

        given()
                .header("Accept", "application/json")
                .when()
                .request(Method.GET, "https://x-clients-be.onrender.com/employee?company=" + companyId )

                .then()
                .statusCode(200)
                .body("", hasSize(greaterThanOrEqualTo(1)))
                .extract().response().jsonPath().getString("id");

    }

    @Test
    @DisplayName("Проверить не получение списока сотрудников по не существующей компании -статус код 200, в теле пустой массив")
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
//    @AfterAll
//    public static void tearDown() throws SQLException {
//
//        ApiService.deleteCompany(token, companyId);
//        connection.close();
//    }



}

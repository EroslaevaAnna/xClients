package tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import lombok.extern.slf4j.Slf4j;
import model.CreateCompany;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import service.ApiService;
import service.ConfHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@Slf4j

public class BusinessEmployeeTest {

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
    public void addNewCompany() {
        final Faker faker = new Faker();
        String name = faker.name().lastName();
        String description = faker.lorem().sentence();
        CreateCompany companybody = new CreateCompany(name, description);

        given()
                .header("x-client-token", token)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .body(companybody)
                .when()
                .post("/company")
                .then()
                .statusCode(201)
                .extract().response().jsonPath().getString("id");

        log.info("Создана новая компания с ID: {}", idCompany);
    }

    @Test
    @DisplayName("Запрашиваем список сотрудников пустой компании")
    public void emptyCompanyList() {
        int idCompany = Integer.valueOf(ApiService.getCompany());
        log.info("idCompany: {}", idCompany);

        given()
                .header("Accept", "application/json")
                .when()
                .request(Method.GET, "https://x-clients-be.onrender.com/employee?company=" + idCompany)
                .then()
                .statusCode(200)
                .body("employees", hasSize(equalTo(0)));
    }

}


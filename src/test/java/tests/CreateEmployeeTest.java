package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ApiService;
import service.ConfHelper;


public class CreateEmployeeTest {

    private static String token;
    private static Integer idEmployee;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = ConfHelper.getProperty("base_uri");
        // получить токен- взять логин пароль из файла проперти, взять тело запроса в виде json, отформатировать, заголовки все это оформит в отдельный класс помошник
        token = ApiService.getToken();
    }


    @Test
    public void iCanCreateNewEmployee() {
       idEmployee = ApiService.;

        System.out.println(token);
       // System.out.println(employeeBody);

    }


}

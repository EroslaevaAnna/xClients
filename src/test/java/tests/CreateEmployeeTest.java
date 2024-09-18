package tests;

import gata.EmployeeData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import service.ApiService;
import service.ConfHelper;


import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sun.security.krb5.Confounder.intValue;


public class CreateEmployeeTest {

    public static String token;
   public static Integer idEmployee;
    private Object connectionToDB;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = ConfHelper.getProperty("base_uri");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        token = ApiService.getToken();
    }

    @Test
    @DisplayName("Добавить нового сотрутдника проверить в БД")
    @Tag("Позитивный")
    public void addNewEmployeeToCheckInDatabase() throws SQLException {

       idEmployee = Integer.valueOf(ApiService.getEmployee());

    }



    @Test
    @DisplayName("Получить сотрудника по id")
    @Tag("Позитивный")
    public void iCanGetEmployeeById(){
        int idEmployee = Integer.parseInt(ApiService.getEmployee());
                ApiService.printEmployeeInfo(idEmployee);
    }

    @Test
    @DisplayName("изменить информацию по сотруднику")
    @Tag("Позитивный")
    public void iCanChangeEmployeeInformation(){
        idEmployee = Integer.valueOf(ApiService.getEmployee());

        int ChangeInfo = Integer.parseInt(String.valueOf(ApiService.ChangeEmployee()));
        ApiService.printEmployeeInfo(ChangeInfo);
    }

}

package tests;


import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import service.ApiService;
import service.ConfHelper;


class DBTest {

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

//        @Test
//        @DisplayName("Добавление сотрудника и проверка его в базе по id")
//        public void CreateEmployeeCheckDB() throws SQLException {
//            idCompany = Integer.valueOf(getCompany());
//            idEmployee = Integer.valueOf(ApiService.getEmployee());
//            int id = idEmployee;
//            boolean isEmployeeInDb = DatabaseEmployeeCheck.isEmployeeInDB(id);
//            assertTrue(isEmployeeInDb);
//            }



    }



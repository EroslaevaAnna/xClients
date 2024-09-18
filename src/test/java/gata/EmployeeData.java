package gata;


import service.ApiService;
import tests.ContractEmployeeTest;

public class EmployeeData {
    public static int id = 0;
    public static String firstName = "Иван";
    public static String lastName = "Иванов";
    public static String middleName = "Иванович";
    public static Integer companyId = Integer.valueOf(ApiService.getCompany());
    public static String email = "testtest@test.ru";
    public static String url = "https://github.com/EroslaevaAnna";
    public static String phone = "4045566" ;
    public static String birthdate = "2004-09-09T18:09:24.238Z";
    public static Boolean isActive = true;
}

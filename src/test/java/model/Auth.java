package model;

public class Auth {

    //тут делаем что бы json не мешал в коде
    private final String username;

    private final String password;

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

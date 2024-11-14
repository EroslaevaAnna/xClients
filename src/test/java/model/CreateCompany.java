package model;

public class CreateCompany {
    private final String name;

    private final String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CreateCompany(String name, String description) {
        this.name = name;
        this.description = description;
    }

}


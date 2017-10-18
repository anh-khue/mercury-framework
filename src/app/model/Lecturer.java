package app.model;

import framework.annotation.model.Column;
import framework.annotation.model.Table;
import framework.model_mapping.model.Model;

@Table(tableName = "lecturer")
public class Lecturer extends Model {
    @Column(value = "first_name")
    private String firstName;
    @Column(value = "last_name")
    private String lastName;

    public Lecturer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

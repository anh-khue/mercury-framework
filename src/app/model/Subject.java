package app.model;

import framework.model_mapping.annotation.model.Column;
import framework.model_mapping.annotation.model.Table;
import framework.model_mapping.model.Model;

@Table(tableName = "subject")
public class Subject extends Model {
    @Column("name")
    private String name;
    @Column("description")
    private String description;
    @Column("price")
    private double price;

    public Subject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

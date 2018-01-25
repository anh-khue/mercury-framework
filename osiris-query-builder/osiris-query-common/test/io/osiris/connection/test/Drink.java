package io.osiris.connection.test;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.Table;
import io.osiris.data.jpa.Entity;

import java.sql.Timestamp;

@Table("drinks")
public class Drink extends Entity {

    @Id
    @Column("id")
    private int id;
    @Column("drink_name")
    private String drinkName;
    @Column("price")
    private double price;
    @Column("created_date")
    private Timestamp createdDate;
    @Column("deleted_date")
    private Timestamp deletedDate;

    public Drink() {
    }

    public Drink(int id, String drinkName, double price, Timestamp createdDate, Timestamp deletedDate) {
        this.id = id;
        this.drinkName = drinkName;
        this.price = price;
        this.createdDate = createdDate;
        this.deletedDate = deletedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Timestamp deletedDate) {
        this.deletedDate = deletedDate;
    }

    @Override
    public String toString() {
        return this.id + " | " + this.drinkName + " | " + this.price;
    }
}
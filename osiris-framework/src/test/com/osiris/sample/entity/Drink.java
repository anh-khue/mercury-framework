package com.osiris.sample.entity;

import com.osiris.core.data.annotation.Column;
import com.osiris.core.data.annotation.OneToMany;
import com.osiris.core.data.annotation.Table;
import com.osiris.core.data.common.Entity;

import java.sql.Timestamp;
import java.util.List;

@Table(table = "drinks")
public class Drink extends Entity {

    @Column("drink_name")
    private String drinkName;

    @Column("price")
    private double price;

    @Column("created_date")
    private Timestamp createdDate;

    @Column("deleted_date")
    private Timestamp deletedDate;

    private List<DrinkHasMaterial> materialList;

    public Drink() {
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

    public Timestamp getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Timestamp deletedDate) {
        this.deletedDate = deletedDate;
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + ", Name: " + getDrinkName()
                + ", Price: " + getPrice()
                + ", Created date: " + getCreatedDate()
                + ", Deleted date: " + getDeletedDate();
    }

    @OneToMany(table = "drink_has_material", referenceColumn = "drink_id")
    public List<DrinkHasMaterial> getMaterialList() {
        return (List<DrinkHasMaterial>) oneToMany();
    }
}

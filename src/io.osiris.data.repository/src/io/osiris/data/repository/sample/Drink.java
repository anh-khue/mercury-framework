package io.osiris.data.repository.sample;

import io.osiris.data.common.annotation.*;
import io.osiris.data.jpa.Entity;

import java.sql.Timestamp;
import java.util.List;

@Table("drinks")
public class Drink extends Entity {

    @Id
    @Generated
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

    private List<DrinkHasMaterial> materialList;

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

    public Drink() {

    }

    public Drink(String drinkName, double price) {
        this.drinkName = drinkName;
        this.price = price;
    }

    @SuppressWarnings("unchecked")
    @OneToMany(target = "id", table = "drink_has_material", column = "drink_id")
    public List<DrinkHasMaterial> getMaterialList() {
        this.materialList = (List<DrinkHasMaterial>) oneToMany();
        return this.materialList;
    }


    @Override
    public String toString() {
        return id + " | " + drinkName + " | " + price + " | " + createdDate + " | " + deletedDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Drink) {
            Drink drink = (Drink) obj;

            return this.id == drink.id
                    && this.drinkName.equals(drink.drinkName)
                    && this.price == drink.price;
        }
        return false;
    }
}

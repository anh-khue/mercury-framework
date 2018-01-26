package io.osiris.data.repository.sample;

import io.osiris.data.common.annotation.*;
import io.osiris.data.jpa.Entity;

import java.sql.Timestamp;
import java.util.List;

@Table("materials")
public class Material extends Entity {

    @Id
    @Generated
    @Column("id")
    int id;

    @Column("material_name")
    String materialName;

    @Column("created_date")
    Timestamp createdDate;

    @Column("deleted_date")
    Timestamp deletedDate;

    private List<DrinkHasMaterial> drinkList;

    public Material() {

    }

    public Material(String materialName, Timestamp createdDate, Timestamp deletedDate) {
        this.materialName = materialName;
        this.createdDate = createdDate;
        this.deletedDate = deletedDate;
    }

    public Material(String materialName) {
        this.materialName = materialName;
    }

    @SuppressWarnings("unchecked")
    @OneToMany(table = "drink_has_material", column = "material_id", target = "id")
    public List<DrinkHasMaterial> getDrinkList() {
        this.drinkList = (List<DrinkHasMaterial>) oneToMany();
        return this.drinkList;
    }

    @Override
    public String toString() {
        return id + " | " + materialName + " | " + createdDate + " | " + deletedDate;
    }
}

package com.osiris.sample.entity;

import com.osiris.core.data.annotation.Column;
import com.osiris.core.data.annotation.OneToMany;
import com.osiris.core.data.annotation.Table;
import com.osiris.core.data.common.Entity;

import java.sql.Timestamp;
import java.util.List;

@Table(table = "materials")
public class Material extends Entity {

    @Column("material_name")
    private String materialName;

    @Column("created_date")
    private Timestamp createdDate;

    @Column("deleted_date")
    private Timestamp deletedDate;

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

    private String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    private Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    private Timestamp getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Timestamp deletedDate) {
        this.deletedDate = deletedDate;
    }

    @OneToMany(table = "drink_has_material", referenceColumn = "material_id")
    public List<DrinkHasMaterial> getDrinkList() {
        return (List<DrinkHasMaterial>) oneToMany();
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + ", Name: " + getMaterialName()
                + ", Created date: " + getCreatedDate()
                + ", Deleted date: " + getDeletedDate();
    }
}

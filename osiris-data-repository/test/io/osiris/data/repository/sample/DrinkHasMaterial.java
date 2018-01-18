package io.osiris.data.repository.sample;

import io.osiris.data.common.annotation.*;
import io.osiris.data.jpa.Entity;

@Table("drink_has_material")
public class DrinkHasMaterial extends Entity {

    @Id
    @Column("id")
    @Generated
    private int id;

    @Id
    @Column("drink_id")
    private int drinkId;

    private Drink drink;

    @Id
    @Column("material_id")
    private int materialId;

    private Material material;

    public DrinkHasMaterial() {

    }

    public DrinkHasMaterial(int drinkId, int materialId) {
        this.drinkId = drinkId;
        this.materialId = materialId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    @ManyToOne(referencedTable = "drinks", column = "drink_id")
    public Drink getDrink() {
        this.drink = (Drink) manyToOne().orElse(null);
        return this.drink;
    }

    @ManyToOne(referencedTable = "materials", column = "material_id")
    public Material getMaterial() {
        this.material = (Material) manyToOne().orElse(null);
        return this.material;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DrinkHasMaterial) {
            DrinkHasMaterial dhm = (DrinkHasMaterial) obj;
            return dhm.getDrinkId() == this.drinkId
                    && dhm.getMaterialId() == this.getMaterialId();
        }
        return false;
    }
}

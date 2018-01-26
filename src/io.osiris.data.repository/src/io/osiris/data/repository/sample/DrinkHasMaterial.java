package io.osiris.data.repository.sample;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.ManyToOne;
import io.osiris.data.common.annotation.Table;
import io.osiris.data.jpa.Entity;

@Table("drink_has_material")
public class DrinkHasMaterial extends Entity {

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

    @ManyToOne(column = "drink_id", table = "drinks", target = "id")
    public Drink getDrink() {
        this.drink = (Drink) manyToOne().orElse(null);
        return this.drink;
    }

    @ManyToOne(column = "material_id", table = "materials", target = "id")
    public Material getMaterial() {
        this.material = (Material) manyToOne().orElse(null);
        return this.material;
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

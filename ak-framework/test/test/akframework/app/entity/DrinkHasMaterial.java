package test.akframework.app.entity;

import com.akframework.core.data.annotation.Column;
import com.akframework.core.data.annotation.CombineKey;
import com.akframework.core.data.annotation.ManyToOne;
import com.akframework.core.data.annotation.Table;
import com.akframework.core.data.common.Entity;

import java.util.Optional;

@Table(table = "drink_has_material")
public class DrinkHasMaterial extends Entity {

    @Column("drink_id")
    @CombineKey
    private int drinkId;

    private Drink drink;

    @Column("material_id")
    @CombineKey
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
        return (Drink) manyToOne().orElse(null);
    }

    @ManyToOne(referencedTable = "materials", column = "material_id")
    public Material getMaterial() {
        return (Material) manyToOne().orElse(null);
    }
}

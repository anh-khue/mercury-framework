package io.osiris.data.jpa;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Generated;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.Table;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityTest {

    @Test
    void idMap() {
        Drink drink = new Drink();
        drink.id = 1;
        Map<String, Serializable> drinkIdMap = drink.idMap();
        assertEquals(drinkIdMap.get("id"), 1);
    }
}


@Table("drinks")
class Drink extends Entity {

    @Id
    @Generated
    @Column("id")
    int id;

    @Column("drink_name")
    String drinkName;

    @Column("price")
    double price;

    @Column("created_date")
    Timestamp createdDate;

    @Column("deleted_date")
    Timestamp deletedDate;

    public Drink() {

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



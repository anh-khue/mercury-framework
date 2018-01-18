package app.osiris.sample;

import com.osiris.data.orm.annotation.Column;
import com.osiris.data.orm.annotation.Table;

@Table(table = "drinks")
public class Drink extends Entity {

    @Column("name")
    private int drinkName;

    @Column("price")
    private double drinkPrice;

    @Column("origin_id")
    private int drinkOriginId;
}


abstract class Entity {

    @Column("id")
    private int id;
}

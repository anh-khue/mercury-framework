package app.osiris.sample;

import com.osiris.data.orm.annotation.Column;
import com.osiris.data.orm.annotation.ManyToOne;
import com.osiris.data.orm.annotation.OneToMany;
import com.osiris.data.orm.annotation.Table;

import java.util.List;

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

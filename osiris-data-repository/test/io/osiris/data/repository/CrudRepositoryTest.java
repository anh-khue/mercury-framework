package io.osiris.data.repository;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.Table;
import io.osiris.data.jpa.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrudRepositoryTest {

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void remove() {
    }

    @Test
    void findAll1() {
    }

    @Test
    void update() {
        Drink drink = new Drink(8, 11);
        drink.setField1("Test field 1");
        drink.setField2("Test field 2");

        DrinkRepo drinkRepo = new DrinkRepo();
        String updateQuery = drinkRepo.update(drink);

        String expected = "UPDATE drink SET test1 = ?,test2 = ?,field1 = ?,field2 = ? WHERE test1 = 8 AND test2 = 11";

        assertEquals(expected, updateQuery);
    }
}


@Table("drink")
class Drink extends Entity {

    @Id
    @Column("test1")
    private int id1;
    @Id
    @Column("test2")
    private int id2;

    @Column("field1")
    private String field1;

    @Column("field2")
    private String field2;

    Drink(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}


class DrinkRepo extends CrudRepository<Drink, Integer> {

}
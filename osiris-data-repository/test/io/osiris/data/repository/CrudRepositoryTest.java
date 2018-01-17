package io.osiris.data.repository;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.Table;
import io.osiris.data.jpa.Entity;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrudRepositoryTest {

    @Test
    void findAll() {
        List<Drink> expectedList = Stream
                .of("Black Coffee,18", "Milk Coffee,20",
                        "Espresso,22", "Americano,25",
                        "Cappuccino,30", "Latte,30")
                .map(s -> s.split(","))
                .map(tuple -> new Drink(tuple[0], Integer.parseInt(tuple[1])))
                .collect(Collectors.toList());

        for (int i = 0; i < 6; i++) {
            expectedList.get(i).id = i + 1;
        }

        System.out.println("Expected: ");
        expectedList.forEach(System.out::println);

        DrinkRepository drinkRepository = new DrinkRepository();
        List<Drink> result = drinkRepository.findAll();

        assertEquals(expectedList.size(), result.size());

        System.out.println("Result: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).toString());
            assertEquals(expectedList.get(i).id, result.get(i).id);
            assertEquals(expectedList.get(i).drinkName, result.get(i).drinkName);
            assertEquals(0, Double.compare(expectedList.get(i).price, result.get(i).price));
        }
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
        Drink drink = new Drink("Cappuccino", 33);
        drink.id = 1;
        drink.createdDate = null;
        drink.deletedDate = null;

        DrinkRepository drinkRepository = new DrinkRepository();
        String updateQuery = drinkRepository.update(drink);

        String expected = "UPDATE drinks SET id = ?,drink_name = ?,price = ?,created_date = ?,deleted_date = ? WHERE id = 1";

        assertEquals(expected, updateQuery);
    }

    @Test
    void insert() {
        DrinkRepository drinkRepository = new DrinkRepository();
        String insertQuery = drinkRepository.insert();

        String expected = "INSERT INTO drinks(id,drink_name,price,created_date,deleted_date) VALUES(?,?,?,?,?)";

        assertEquals(expected, insertQuery);
    }
}


@Table("drinks")
class Drink extends Entity {

    @Id
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

    Drink(String drinkName, double price) {
        this.drinkName = drinkName;
        this.price = price;
    }
}


class DrinkRepository extends CrudRepository<Drink, Integer> {

}
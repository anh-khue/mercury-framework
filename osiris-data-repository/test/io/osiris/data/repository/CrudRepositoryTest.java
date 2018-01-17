package io.osiris.data.repository;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Generated;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.Table;
import io.osiris.data.jpa.Entity;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
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
            assertEquals(expectedList.get(i), result.get(i));
        }
    }

    @Test
    void findById() {
        Drink expected = new Drink("Americano", 25);
        expected.id = 4;
        System.out.println(expected);

        DrinkRepository drinkRepository = new DrinkRepository();
        Optional<Drink> result = drinkRepository.findById(4);

        result.ifPresent(drink -> {
            System.out.println(drink);
            assertEquals(expected, drink);
        });
    }

    @Test
    void save() {
//        Save for Insert new Entity

        Drink insertedDrink = new Drink("Matcha", 33);
        System.out.println(insertedDrink);

        DrinkRepository drinkRepository = new DrinkRepository();
        drinkRepository.save(insertedDrink);

        List<Drink> listAfterInsert = drinkRepository.findAll();

        int lastIndex = listAfterInsert.size() - 1;
        Drink result = listAfterInsert.get(lastIndex);

        insertedDrink.id = result.id;
        System.out.println(result);

        assertEquals(insertedDrink, result);

        drinkRepository.remove(result.id);

//        Save for Update Entity
        Optional<Drink> drinkForUpdate = drinkRepository.findById(1);

        drinkForUpdate.ifPresent(forUpdate -> {
            forUpdate.drinkName = "Vietnamese Black Coffee";
            forUpdate.price = 19;
            System.out.println(forUpdate);
            drinkRepository.save(forUpdate);

            Optional<Drink> drinkAfterUpdate = drinkRepository.findById(1);

            drinkAfterUpdate.ifPresent(afterUpdate -> {
                System.out.println(afterUpdate);
                assertEquals(forUpdate, afterUpdate);
            });
        });

        Optional<Drink> revertUpdate = drinkRepository.findById(1);

        revertUpdate.ifPresent(revert -> {
            revert.drinkName = "Black Coffee";
            revert.price = 18;
            System.out.println(revert);
            drinkRepository.save(revert);
        });
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

    Drink(String drinkName, double price) {
        this.drinkName = drinkName;
        this.price = price;
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


class DrinkRepository extends CrudRepository<Drink, Integer> {

}
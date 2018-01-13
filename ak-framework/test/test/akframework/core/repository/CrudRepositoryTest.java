package test.akframework.core.repository;

import org.junit.Test;
import test.akframework.app.Drink;
import test.akframework.app.DrinkRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class CrudRepositoryTest {

    @Test
    public void findAll() {
        List<DrinkTest> testList = Stream
                .of("Black Coffee,18", "Milk Coffee,20",
                        "Espresso,22", "Americano,25",
                        "Cappuccino,30", "Latte,30")
                .map(s -> s.split(","))
                .map(tuple -> new DrinkTest(tuple[0], Integer.parseInt(tuple[1])))
                .collect(Collectors.toList());

        for (int i = 0; i < 6; i++) {
            testList.get(i).id = i + 1;
        }

        System.out.println("Expected: ");
        testList.forEach(System.out::println);

        DrinkRepository drinkRepository = new DrinkRepository();
        List<Drink> result = drinkRepository.findAll();

        assertEquals(testList.size(), result.size());

        System.out.println("Result: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).toString());
            assertEquals(testList.get(i).id, result.get(i).getId());
            assertEquals(testList.get(i).drinkName, result.get(i).getDrinkName());
            assertEquals(0, Double.compare(testList.get(i).price, result.get(i).getPrice()));
        }
    }


    @Test
    public void findById() {
        DrinkTest test = new DrinkTest(4, "Americano", 25);
        System.out.println(test);

        DrinkRepository drinkRepository = new DrinkRepository();
        Optional<Drink> result = drinkRepository.findById(4);

        result.ifPresent(drink -> {
            System.out.println(drink);
            assertEquals(test.id, drink.getId());
            assertEquals(test.drinkName, drink.getDrinkName());
            assertEquals(0, Double.compare(test.price, drink.getPrice()));
        });
    }

    @Test
    public void save() {
//        Save for Insert new Entity
        DrinkRepository drinkRepository = new DrinkRepository();

        Drink drink = new Drink();
        drink.setDrinkName("Matcha");
        drink.setPrice(33);
        System.out.println(drink);
        drinkRepository.save(drink);

        List<Drink> listAfterInsert = drinkRepository.findAll();
        int lastIndex = listAfterInsert.size() - 1;
        Drink result = listAfterInsert.get(lastIndex);
        System.out.println(result);

        assertEquals(drink.getDrinkName(), result.getDrinkName());
        assertEquals(0, Double.compare(drink.getPrice(), result.getPrice()));
        assertEquals(drink.getDeletedDate(), result.getDeletedDate());
    }

    @Test
    public void remove() {

    }
}


class DrinkTest {

    int id;
    String drinkName;
    double price;

    DrinkTest(int id, String drinkName, double price) {
        this.id = id;
        this.drinkName = drinkName;
        this.price = price;
    }

    DrinkTest(String drinkName, double price) {
        this.drinkName = drinkName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + drinkName + ", Price: " + price;
    }
}
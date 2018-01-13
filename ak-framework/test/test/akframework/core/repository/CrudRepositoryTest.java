package test.akframework.core.repository;

import org.junit.Assert;
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

        Drink drinkInsert = new Drink();
        drinkInsert.setDrinkName("Matcha");
        drinkInsert.setPrice(33);
        System.out.println(drinkInsert);
        drinkRepository.save(drinkInsert);

        List<Drink> listAfterInsert = drinkRepository.findAll();
        int lastIndex = listAfterInsert.size() - 1;
        Drink result = listAfterInsert.get(lastIndex);
        System.out.println(result);

        assertEquals(drinkInsert.getDrinkName(), result.getDrinkName());
        assertEquals(0, Double.compare(drinkInsert.getPrice(), result.getPrice()));
        assertEquals(drinkInsert.getDeletedDate(), result.getDeletedDate());


//        Save for Update Entity
        Optional<Drink> beforeUpdate = drinkRepository.findById(1);

        beforeUpdate.ifPresent(before -> {
            before.setDrinkName("Vietnamese Black Coffee");
            before.setPrice(19);
            System.out.println(before);
            drinkRepository.save(before);

            Optional<Drink> afterUpdate = drinkRepository.findById(1);

            afterUpdate.ifPresent(after -> {
                System.out.println(after);
                assertEquals(before.getDrinkName(), after.getDrinkName());
                assertEquals(0, Double.compare(before.getPrice(), after.getPrice()));
                assertEquals(before.getCreatedDate(), after.getCreatedDate());
                assertEquals(before.getDeletedDate(), after.getDeletedDate());
            });
        });
    }

    @Test
    public void remove() {
        DrinkRepository drinkRepository = new DrinkRepository();

        Drink drinkInsert = new Drink();
        drinkInsert.setDrinkName("Chocolate");
        drinkInsert.setPrice(35);
        System.out.println(drinkInsert);
        drinkRepository.save(drinkInsert);

        List<Drink> listAfterInsert = drinkRepository.findAll();
        Drink afterInsert = listAfterInsert.get(listAfterInsert.size() - 1);
        System.out.println(afterInsert);

        assertEquals(drinkInsert.getDrinkName(), afterInsert.getDrinkName());
        assertEquals(0, Double.compare(drinkInsert.getPrice(), afterInsert.getPrice()));
        assertEquals(drinkInsert.getDeletedDate(), afterInsert.getDeletedDate());

        drinkRepository.remove(afterInsert.getId());

        List<Drink> listAfterDelete = drinkRepository.findAll();
        assertNotEquals(listAfterInsert.size(), listAfterDelete.size());
        assertFalse(drinkRepository.findById(afterInsert.getId()).isPresent());
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
package io.osiris.data.repository.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    public static void main(String[] args) {
        findAll();
        findById();
        save();
        manyToOne();
        oneToMany();
    }

    static void findAll() {
        List<Drink> expectedList = Stream
                .of("Black Coffee,18", "Milk Coffee,20",
                        "Espresso,22", "Americano,25",
                        "Cappuccino,30", "Latte,30")
                .map(s -> s.split(","))
                .map(tuple -> new Drink(tuple[0], Integer.parseInt(tuple[1])))
                .collect(Collectors.toList());

        for (int i = 0; i < 6; i++) {
            expectedList.get(i).setId(i + 1);
        }

        System.out.println("Expected: ");
        expectedList.forEach(System.out::println);

        DrinkRepository drinkRepository = new DrinkRepository();
        List<Drink> result = drinkRepository.findAll();

        System.out.println("Result: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).toString());
        }
    }

    static void findById() {
        Drink expected = new Drink("Americano", 25);
        expected.setId(4);
        System.out.println(expected);

        DrinkRepository drinkRepository = new DrinkRepository();
        Optional<Drink> result = drinkRepository.findById(4);

        result.ifPresent(drink -> {
            System.out.println(drink);
        });
    }

    static void save() {
//        Save for Insert new Entity

        Drink insertedDrink = new Drink("Matcha", 33);
        System.out.println(insertedDrink);

        DrinkRepository drinkRepository = new DrinkRepository();
        drinkRepository.save(insertedDrink);

        List<Drink> listAfterInsert = drinkRepository.findAll();

        int lastIndex = listAfterInsert.size() - 1;
        Drink result = listAfterInsert.get(lastIndex);

        insertedDrink.setId(result.getId());
        System.out.println(result);

        drinkRepository.remove(result.getId());

//        Save for Update Entity
        Optional<Drink> drinkForUpdate = drinkRepository.findById(1);

        drinkForUpdate.ifPresent(forUpdate -> {
            forUpdate.setDrinkName("Vietnamese Black Coffee");
            forUpdate.setPrice(19);
            System.out.println(forUpdate);
            drinkRepository.save(forUpdate);

            Optional<Drink> drinkAfterUpdate = drinkRepository.findById(1);

            drinkAfterUpdate.ifPresent(afterUpdate -> {
                System.out.println(afterUpdate);
            });
        });

        Optional<Drink> revertUpdate = drinkRepository.findById(1);

        revertUpdate.ifPresent(revert -> {
            revert.setDrinkName("Black Coffee");
            revert.setPrice(18);
            System.out.println(revert);
            drinkRepository.save(revert);
        });
    }

    static void manyToOne() {
        DrinkRepository drinkRepository = new DrinkRepository();
        Drink cappuccino = drinkRepository.findById(5)
                .orElse(null);

        List<DrinkHasMaterial> materialList = cappuccino.getMaterialList();

        for (DrinkHasMaterial drinkHasMaterial : materialList) {
            Drink drinkFromList = drinkHasMaterial.getDrink();
        }
    }

    static void oneToMany() {
        DrinkRepository drinkRepository = new DrinkRepository();
        Drink milkCoffee = drinkRepository.findById(2)
                .orElse(null);

        List<DrinkHasMaterial> materialList = milkCoffee.getMaterialList();

        List<DrinkHasMaterial> expectedList = new ArrayList<>();
        expectedList.add(new DrinkHasMaterial(2, 1));
        expectedList.add(new DrinkHasMaterial(2, 2));
        expectedList.add(new DrinkHasMaterial(2, 3));

        for (int i = 0; i < materialList.size(); i++) {
            System.out.println(materialList.get(i));
        }
    }
}

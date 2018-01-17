package io.osiris.data.jpa;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Id;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityTest {

    @Test
    void idMap() {
        Drink drink = new Drink(8, 11);
        Map<String, Serializable> idMap = drink.idMap();
        assertEquals(idMap.get("test1"), 8);
        assertEquals(idMap.get("test2"), 11);
    }

    @Test
    void manyToOne() {

    }

    @Test
    void oneToMany() {

    }
}


class Drink extends Entity {

    @Id
    @Column("test1")
    private int testId1;
    @Id
    @Column("test2")
    private int testId2;

    Drink(int testId1, int testId2) {
        this.testId1 = testId1;
        this.testId2 = testId2;
    }

    public int getTestId1() {
        return testId1;
    }

    public void setTestId1(int testId1) {
        this.testId1 = testId1;
    }

    public int getTestId2() {
        return testId2;
    }

    public void setTestId2(int testId2) {
        this.testId2 = testId2;
    }
}
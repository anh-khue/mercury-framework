package io.osiris.connection.test;

import io.osiris.data.connection.ConnectionFactory;
import io.osiris.query.common.Query;
import io.osiris.query.tuple.Quadruplet;
import io.osiris.query.tuple.Triplet;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static io.osiris.query.common.QueryPredicates.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommonTest {

    @Test
    void connectionTest() throws SQLException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = factory.openConnection();
        assertNotNull(connection);
    }

    @SuppressWarnings("unchecked")
    @Test
    void builderTest() {
        List<Drink> drinkList1 =
                (List<Drink>) Query.build().from(Drink.class)
                        .where(Quadruplet.create("price", BETWEEN, 19, 27))
                        .getResultList();
        System.out.println(drinkList1);

        List<Drink> drinkList2 =
                (List<Drink>) Query.build().from(Drink.class)
                        .where(Triplet.create("drink_name", LIKE, "%Coffee%"))
                        .getResultList();
        System.out.println(drinkList2);
    }
}
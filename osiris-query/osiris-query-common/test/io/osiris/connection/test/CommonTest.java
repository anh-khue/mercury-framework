package io.osiris.connection.test;

import io.osiris.data.common.annotation.Column;
import io.osiris.data.common.annotation.Id;
import io.osiris.data.common.annotation.Table;
import io.osiris.data.connection.ConnectionFactory;
import io.osiris.data.jpa.Entity;
import io.osiris.query.common.builder.MySqlBuilder;
import io.osiris.query.common.builder.QueryBuilder;
import io.osiris.query.common.predicate.QueryPredicates;
import io.osiris.query.tuple.Pair;
import io.osiris.query.tuple.Quadruplet;
import io.osiris.query.tuple.Triplet;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static io.osiris.query.common.predicate.QueryPredicates.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommonTest {

    @Test
    void connectionTest() throws SQLException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = factory.openConnection();
        assertNotNull(connection);
    }

    @Test
    void builderTest() {
        QueryBuilder queryBuilder = new MySqlBuilder();

        List<Drink> drinkList =
                (List<Drink>) queryBuilder.from(Drink.class)
                        .where(Quadruplet.create("price", BETWEEN, 19, 27))
                        .getResultList();
        System.out.println(drinkList);
    }
}
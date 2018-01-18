package io.osiris.data.connection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory extends Serializable {

    Connection openConnection() throws ClassNotFoundException, SQLException;

}
package io.osiris.data.connection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionAdapter extends Serializable {

    Connection openConnection() throws ClassNotFoundException, SQLException;
}
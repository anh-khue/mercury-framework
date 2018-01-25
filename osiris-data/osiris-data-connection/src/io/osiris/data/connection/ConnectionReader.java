package io.osiris.data.connection;

public interface ConnectionReader {
    String getDriverClass();

    String getUrl();

    String getUsername();

    String getPassword();
}

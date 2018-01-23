package io.osiris.data.connection;

public interface ConnectionReadable {
    String getDriverClass();

    String getUrl();

    String getUsername();

    String getPassword();
}

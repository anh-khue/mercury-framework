package io.osiris.query.common.builder;

public interface WhereComponent extends ComponentBuilder {

    WhereComponent andWhere(String param1, String param2, String param3);

    WhereComponent andWhere(String param1, String param2, int param3);

    WhereComponent andWhere(String param1, String param2, double param3);

    WhereComponent andWhere(String param1, String param2, String param3, String param4);

    WhereComponent andWhere(String param1, String param2, int param3, int param4);

    WhereComponent andWhere(String param1, String param2, double param3, double param4);

    WhereComponent orWhere(String param1, String param2, String param3);

    WhereComponent orWhere(String param1, String param2, int param3);

    WhereComponent orWhere(String param1, String param2, double param3);

    WhereComponent orWhere(String param1, String param2, String param3, String param4);

    WhereComponent orWhere(String param1, String param2, int param3, int param4);

    WhereComponent orWhere(String param1, String param2, double param3, double param4);

}

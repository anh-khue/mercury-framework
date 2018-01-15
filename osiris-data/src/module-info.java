module osiris.data {
    requires osiris.data.orm;
    requires kotlin.stdlib;

    opens com.osiris.data to osiris.data.orm;

    exports com.osiris.data;
}
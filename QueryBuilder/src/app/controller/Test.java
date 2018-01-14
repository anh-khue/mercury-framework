package app.controller;

import app.model.Lecturer;
import framework.builder.abstract_builder.QueryBuilder;
import framework.util.Query;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List lecturerList = Query.table(Lecturer.class)
                .orderBy(new Object[][]{
                        {"last_name", QueryBuilder.QueryOrder.ASC},
                        {"id", QueryBuilder.QueryOrder.DESC}
                })
                .getResultList();
        lecturerList.forEach(System.out::println);
    }
}
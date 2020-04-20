package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import org.springframework.stereotype.Repository;

@Repository
public class AttendRepository {

    private static final String RESPONSE_MESSAGE = "Hello_World!";

    public String getParameter(String name) {
        System.out.println(name);
        return RESPONSE_MESSAGE;
    }
}

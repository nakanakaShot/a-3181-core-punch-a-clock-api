package com.herokuapp.a3181core.punchaclockdev.infrastructure;

import org.springframework.stereotype.Repository;

@Repository
public class AttendRepository {

    public void getParameter(String name) {
        System.out.println(name);
    }
}

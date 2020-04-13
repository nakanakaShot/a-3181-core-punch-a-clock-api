package com.herokuapp.a3181core.punchaclockdev.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @RequestMapping()
  public String hello() {
    return "Hello World";
  }
}

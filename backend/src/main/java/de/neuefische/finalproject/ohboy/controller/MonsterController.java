package de.neuefische.finalproject.ohboy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monster")
public class MonsterController {

    @GetMapping
    public String setup() {
        return "Home";
    }

}

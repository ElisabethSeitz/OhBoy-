package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monster")
public class MonsterController {

    private final MonsterService monsterService;

    @Autowired
    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    @GetMapping
    public List<Monster> getAll() {
        return monsterService.getAll();
    }

}

package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value= "/api/monster")
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

    @GetMapping("{userId}")
    public List<Monster> getAllByUserId(@PathVariable String userId) {
        return monsterService.findAllByUserId(userId);
    }

    @PostMapping
    public Monster add(@RequestBody AddMonsterDto dto){
        return this.monsterService.add(dto);
    }

}

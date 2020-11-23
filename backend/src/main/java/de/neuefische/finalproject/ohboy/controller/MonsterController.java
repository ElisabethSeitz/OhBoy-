package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.dto.RemoveMonsterDto;
import de.neuefische.finalproject.ohboy.dto.UpdateMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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

    @PutMapping("{monsterId}")
    public Monster update(@RequestBody UpdateMonsterDto updatedMonster, @PathVariable String monsterId) {
        if(!monsterId.equals(updatedMonster.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return monsterService.update(updatedMonster);
    }

    @DeleteMapping("{monsterId}")
    public void remove(@RequestBody RemoveMonsterDto removeMonster, @PathVariable String monsterId) {
        if(!monsterId.equals(removeMonster.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        monsterService.remove(removeMonster);
    }

}

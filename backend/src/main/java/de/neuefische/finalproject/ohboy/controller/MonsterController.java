package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.dto.UpdateMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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
    public List<Monster> getAllByUserId(Principal principal) {
        return monsterService.findAllByUserId(principal.getName());
    }

    @PostMapping
    public Monster add(@RequestBody AddMonsterDto dto, Principal principal){
        return this.monsterService.add(dto, principal.getName());
    }

    @PutMapping("{monsterId}")
    public Monster update(@RequestBody UpdateMonsterDto updatedMonster, @PathVariable String monsterId, Principal principal) {
        if(!monsterId.equals(updatedMonster.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return monsterService.update(updatedMonster, principal.getName());
    }

    @DeleteMapping("{monsterId}")
    public void remove(@PathVariable String monsterId, Principal principal) {
        monsterService.remove(monsterId, principal.getName());
    }

}

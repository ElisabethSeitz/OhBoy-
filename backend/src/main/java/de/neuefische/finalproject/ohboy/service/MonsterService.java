package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.dto.UpdateMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class MonsterService {

    private final MonsterMongoDao monsterMongoDao;
    private final IdUtils idUtils;


    @Autowired
    public MonsterService(MonsterMongoDao monsterMongoDao, IdUtils idUtils) {
        this.monsterMongoDao = monsterMongoDao;
        this.idUtils = idUtils;
    }

    public List<Monster> findAllByUserId(String userId) {
        return monsterMongoDao.findAllByUserId(userId);
    }

    public Monster add(AddMonsterDto dto, String userId) {
        Monster monsterToBeSaved = Monster.builder()
                                        .id(idUtils.generateId())
                                        .name(dto.getName())
                                        .userId(userId)
                                        .image(dto.getImage())
                                        .build();
        return monsterMongoDao.save(monsterToBeSaved);
    }

    public Monster update(UpdateMonsterDto update, String userId) {
        Monster monster = monsterMongoDao.findById(update.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!Objects.equals(monster.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Monster updatedMonster = Monster.builder()
                .id(update.getId())
                .userId(userId)
                .name(update.getName())
                .image(update.getImage())
                .balance(monster.getBalance())
                .payoutDoneRewards(monster.getPayoutDoneRewards())
                .scoreDoneTasks(monster.getScoreDoneTasks())
                .build();

        return monsterMongoDao.save(updatedMonster);
    }

    public void remove(String monsterId, String userId) {
        Monster monster = monsterMongoDao.findById(monsterId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!Objects.equals(monster.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        monsterMongoDao.deleteById(monsterId);
    }
}

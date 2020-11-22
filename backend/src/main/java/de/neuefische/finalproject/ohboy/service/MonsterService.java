package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import de.neuefische.finalproject.ohboy.utils.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonsterService {

    private final MonsterMongoDao monsterMongoDao;
    private final IdUtils idUtils;


    @Autowired
    public MonsterService(MonsterMongoDao monsterMongoDao, IdUtils idUtils) {
        this.monsterMongoDao = monsterMongoDao;
        this.idUtils = idUtils;
    }

    public List<Monster> getAll() {
        return monsterMongoDao.findAll();
    }

    public List<Monster> findAllByUserId(String userId) {
        return monsterMongoDao.findAllByUserId(userId);
    }

    public Monster add(AddMonsterDto dto) {
        Monster monsterToBeSaved = Monster.builder()
                                        .id(idUtils.generateId())
                                        .name(dto.getName())
                                        .userId(dto.getUserId())
                                        .image(dto.getImage())
                                        .build();
        return monsterMongoDao.save(monsterToBeSaved);
    }
}

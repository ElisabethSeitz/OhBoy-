package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.model.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonsterService {

    private final MonsterMongoDao monsterMongoDao;

    @Autowired
    public MonsterService(MonsterMongoDao monsterMongoDao) {
        this.monsterMongoDao = monsterMongoDao;
    }

    public List<Monster> getAll() {
        return monsterMongoDao.findAll();
    }

}

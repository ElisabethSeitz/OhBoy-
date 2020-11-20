package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddMonsterDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class MonsterServiceTest {

    //Given
    final IdUtils idUtils = mock(IdUtils.class);
    final MonsterMongoDao monsterMongoDao = mock(MonsterMongoDao.class);

    final MonsterService monsterService = new MonsterService(monsterMongoDao, idUtils);

    final List<Monster> monsters = new ArrayList<>(List.of(
            new Monster("some id", "@facebookSomeUserId", "some name", "some image", 0, 0, 0, 0, 0, 0, 0),
            new Monster("some id2", "@facebookSomeUserId2", "some name2", "some image2", 0, 0, 0, 0, 0, 0, 0),
            new Monster("some id3", "@facebookSomeUserId3", "some name3", "some image3", 0, 0, 0, 0, 0, 0, 0),
            new Monster("some id4", "@facebookSomeUserI4", "some name4", "some image4", 0, 0, 0, 0, 0, 0, 0)
    ));

    final List<Monster> getStockMonsters(){
        return monsters;
    }

    @Test
    @DisplayName("The \"getAll\" method should return all Monster objects in a list")
    void getAll() {
        when(monsterMongoDao.findAll()).thenReturn(getStockMonsters());

        //When
        List<Monster> allMonsters = monsterService.getAll();

        //Then
        assertThat(allMonsters, containsInAnyOrder(getStockMonsters().toArray()));
    }

    @Test
    @DisplayName("The \"findAllByUserId\" method should return all Monster objects that match the UserId in a list")
    void findAllByUserId() {
        //Given
        String userId = "@facebookSomeUserId";

        List<Monster> expectedMonsters = new ArrayList<>(List.of(new Monster(
                "userId", userId, "some name", "some image", 0, 0, 0, 0, 0, 0, 0
        )));

        when(monsterMongoDao.findAllByUserId(userId)).thenReturn(expectedMonsters);

        //When
        List<Monster> resultMonsters = monsterService.findAllByUserId(userId);

        //Then
        assertThat(resultMonsters, is(expectedMonsters));
    }

    @Test
    @DisplayName("The \"add\" method should return the added Monster object")
    void add() {
        //Given
        String expectedId = "randomId";

        AddMonsterDto monsterDto = new AddMonsterDto(
                "some name",
                "some UserId",
                "some image"
        );

        Monster expectedMonster = new Monster(
                expectedId,
                "some UserId",
                "some name",
                "some image",
                0, 0, 0, 0, 0, 0, 0
        );

        when(idUtils.generateId()).thenReturn(expectedId);
        when(monsterMongoDao.save(expectedMonster)).thenReturn(expectedMonster);

        //When
        Monster newMonster = monsterService.add(monsterDto);

        //Then
        assertThat(newMonster, is(expectedMonster));
        verify(monsterMongoDao).save(expectedMonster);
    }
}
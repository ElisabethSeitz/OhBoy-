package de.neuefische.finalproject.ohboy.service;

import de.neuefische.finalproject.ohboy.dao.MonsterMongoDao;
import de.neuefische.finalproject.ohboy.dao.RewardMongoDao;
import de.neuefische.finalproject.ohboy.dto.AddRewardDto;
import de.neuefische.finalproject.ohboy.dto.UpdateRewardDto;
import de.neuefische.finalproject.ohboy.model.Monster;
import de.neuefische.finalproject.ohboy.model.Reward;
import de.neuefische.finalproject.ohboy.model.Status;
import de.neuefische.finalproject.ohboy.utils.IdUtils;
import de.neuefische.finalproject.ohboy.utils.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class RewardService {

    private final RewardMongoDao rewardMongoDao;
    private final IdUtils idUtils;
    private final TimestampUtils timestampUtils;
    private final MonsterMongoDao monsterMongoDao;

    @Autowired
    public RewardService(RewardMongoDao rewardMongoDao, IdUtils idUtils, TimestampUtils timestampUtils, MonsterMongoDao monsterMongoDao) {
        this.rewardMongoDao = rewardMongoDao;
        this.idUtils = idUtils;
        this.timestampUtils = timestampUtils;
        this.monsterMongoDao = monsterMongoDao;
    }

    public List<Reward> findAllByMonsterId(String monsterId) {
        return rewardMongoDao.findAllByMonsterId(monsterId);
    }

    public Reward add(AddRewardDto dto, String monsterId, String userId) {
        Monster monster = monsterMongoDao.findById(monsterId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (!Objects.equals(monster.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Reward rewardToBeSaved = Reward.builder()
                .id(idUtils.generateId())
                .userId(userId)
                .monsterId(monsterId)
                .description(dto.getDescription())
                .score(dto.getScore())
                .status(Status.OPEN)
                .build();
        return rewardMongoDao.save(rewardToBeSaved);
    }

    public Reward update(UpdateRewardDto update, String userId) {
        Reward reward = rewardMongoDao.findById(update.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!Objects.equals(reward.getUserId(), userId) || reward.getStatus().equals(Status.DONE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Reward updatedReward = Reward.builder()
                .id(update.getId())
                .userId(userId)
                .monsterId(reward.getMonsterId())
                .description(update.getDescription())
                .score(update.getScore())
                .status(reward.getStatus())
                .build();

        return rewardMongoDao.save(updatedReward);
    }

    public Reward updateStatus(String rewardId, String monsterId, String userId) {
        Reward reward = rewardMongoDao.findById(rewardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Monster monster = monsterMongoDao.findById(monsterId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!Objects.equals(reward.getUserId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if(reward.getStatus().equals(Status.OPEN)){
            reward.setStatus(Status.DONE);
            reward.setTimestampOfDone(timestampUtils.generateTimeStampEpochSeconds());
            monster.setPayoutDoneRewards(monster.getPayoutDoneRewards() + reward.getScore());
        } else {reward.setStatus(Status.OPEN);
            reward.setTimestampOfDone(null);
            monster.setPayoutDoneRewards(monster.getPayoutDoneRewards() - reward.getScore());
        }
        monsterMongoDao.save(monster);

        return rewardMongoDao.save(reward);
    }

    public void remove(String rewardId, String userId) {
        Reward reward = rewardMongoDao.findById(rewardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!Objects.equals(reward.getUserId(), userId) || reward.getStatus().equals(Status.DONE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        rewardMongoDao.deleteById(rewardId);
    }
}
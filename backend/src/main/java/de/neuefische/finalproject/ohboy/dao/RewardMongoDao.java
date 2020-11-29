package de.neuefische.finalproject.ohboy.dao;

import de.neuefische.finalproject.ohboy.model.Reward;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RewardMongoDao extends PagingAndSortingRepository<Reward,String> {

    List<Reward> findAllByMonsterId(String monsterId);
}

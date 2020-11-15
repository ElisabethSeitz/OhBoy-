package de.neuefische.finalproject.ohboy.dao;

import de.neuefische.finalproject.ohboy.model.Monster;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MonsterMongoDao extends PagingAndSortingRepository<Monster,String> {

    List<Monster> findAll();
}

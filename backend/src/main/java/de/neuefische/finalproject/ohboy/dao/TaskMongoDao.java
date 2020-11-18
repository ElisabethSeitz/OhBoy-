package de.neuefische.finalproject.ohboy.dao;

import de.neuefische.finalproject.ohboy.model.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TaskMongoDao extends PagingAndSortingRepository<Task,String> {

    List<Task> findAllByMonsterId(String monsterId);
}

package de.neuefische.finalproject.ohboy.dao;


import de.neuefische.finalproject.ohboy.model.OhBoyUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<OhBoyUser, String> {
}
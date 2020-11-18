package de.neuefische.finalproject.ohboy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "monster")
public class Monster {
    @Id
    private String id;
    private String userId;
    private String name;
    private int balance;
    private int payoutDoneRewards;
    private int scoreDoneTasks;
    private int countOpenTasks;
    private int countDoneTasks;
    private int countOpenRewards;
    private int countDoneRewards;
}

package de.neuefische.finalproject.ohboy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMonsterDto {

    private String id;
    private String userId;
    private String name;
    private String image;
    private int balance;
    private int payoutDoneRewards;
    private int scoreDoneTasks;
    private int countOpenTasks;
    private int countDoneTasks;
    private int countOpenRewards;
    private int countDoneRewards;
}

package de.neuefische.finalproject.ohboy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRewardDto {

    private String description;
    private int score;
}
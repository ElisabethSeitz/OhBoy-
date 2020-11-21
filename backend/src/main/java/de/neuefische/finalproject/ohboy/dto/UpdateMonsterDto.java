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
}

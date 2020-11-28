package de.neuefische.finalproject.ohboy.controller;

import de.neuefische.finalproject.ohboy.dto.AddRewardDto;
import de.neuefische.finalproject.ohboy.dto.AddTaskDto;
import de.neuefische.finalproject.ohboy.dto.UpdateRewardDto;
import de.neuefische.finalproject.ohboy.dto.UpdateTaskDto;
import de.neuefische.finalproject.ohboy.model.Reward;
import de.neuefische.finalproject.ohboy.model.Task;
import de.neuefische.finalproject.ohboy.service.RewardService;
import de.neuefische.finalproject.ohboy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/monster/")
public class RewardController {

    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("{monsterId}/rewards")
    public List<Reward> getAllByMonsterId(@PathVariable String monsterId) {
        return rewardService.findAllByMonsterId(monsterId);
    }

    @PostMapping("{monsterId}/rewards")
    public Reward add(@RequestBody AddRewardDto dto, @PathVariable String monsterId, Principal principal){
        return this.rewardService.add(dto, monsterId, principal.getName());
    }

    @PutMapping("{monsterId}/rewards/{rewardId}")
    public Reward update(@RequestBody UpdateRewardDto updatedReward, @PathVariable String rewardId, Principal principal) {
        if(!rewardId.equals(updatedReward.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return rewardService.update(updatedReward, principal.getName());
    }

    @PutMapping("{monsterId}/rewards/{rewardId}/status")
    public Reward updateStatus(@PathVariable String rewardId, @PathVariable String monsterId, Principal principal) {
        return rewardService.updateStatus(rewardId, monsterId, principal.getName());
    }

    @DeleteMapping("{monsterId}/rewards/{rewardId}")
    public void remove(@PathVariable String rewardId, Principal principal) {
        rewardService.remove(rewardId, principal.getName());
    }
}

package com.fifaminer.service.group.impl;

import com.fifaminer.entity.PlayerGroup;
import com.fifaminer.repository.PlayerGroupRepository;
import com.fifaminer.service.group.PlayerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fifaminer.util.CollectionsUtils.transformToList;
import static com.fifaminer.util.CollectionsUtils.transformToSet;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class PlayerGroupServiceIml implements PlayerGroupService {

    private final PlayerGroupRepository playerGroupRepository;

    @Autowired
    public PlayerGroupServiceIml(PlayerGroupRepository playerGroupRepository) {
        this.playerGroupRepository = playerGroupRepository;
    }

    @Override
    public List<Long> findPlayersByGroup(String groupName) {
        PlayerGroup playerGroup = playerGroupRepository.findOne(groupName);
        return isNull(playerGroup) ? emptyList() : transformToList(playerGroup.getPlayerIds());
    }

    @Override
    public void addPlayersToGroup(List<Long> playerIds, String groupName) {
        if (playerGroupRepository.exists(groupName)) {
            PlayerGroup playerGroup = playerGroupRepository.findOne(groupName);
            playerGroup.getPlayerIds().addAll(playerIds);
            playerGroupRepository.save(playerGroup);
        } else {
            playerGroupRepository.save(new PlayerGroup(groupName, transformToSet(playerIds)));
        }
    }

    @Override
    public void removePlayersFromGroup(List<Long> playerIds, String groupName) {
        PlayerGroup playerGroup = playerGroupRepository.findOne(groupName);
        if (nonNull(playerGroup)) {
            playerGroup.getPlayerIds().removeAll(playerIds);
            playerGroupRepository.save(playerGroup);
        }
    }

    @Override
    public void deleteGroup(String groupName) {
        playerGroupRepository.delete(groupName);
    }
}

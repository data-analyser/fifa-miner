package com.fifaminer.service.group;

import java.util.List;

public interface PlayerGroupService {

    List<Long> findPlayersByGroup(String groupName);

    void addPlayersToGroup(List<Long> playerIds, String groupName);

    void removePlayersFromGroup(List<Long> playerIds, String groupName);

    void deleteGroup(String groupName);
}

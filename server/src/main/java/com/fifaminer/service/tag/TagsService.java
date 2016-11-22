package com.fifaminer.service.tag;

import java.util.List;

public interface TagsService {

    List<Long> findPlayersByTag(String tagName);

    void markPayersWithTag(List<Long> playerIds, String tagName);

    void removeTagFromPlayers(List<Long> playerIds, String tagName);
}

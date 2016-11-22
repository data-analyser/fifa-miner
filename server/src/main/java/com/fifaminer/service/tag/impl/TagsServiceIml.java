package com.fifaminer.service.tag.impl;

import com.fifaminer.entity.PlayerTag;
import com.fifaminer.entity.Tag;
import com.fifaminer.repository.PlayerTagRepository;
import com.fifaminer.repository.TagRepository;
import com.fifaminer.service.tag.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class TagsServiceIml implements TagsService {

    private final TagRepository tagRepository;
    private final PlayerTagRepository playerTagRepository;

    @Autowired
    public TagsServiceIml(TagRepository tagRepository,
                          PlayerTagRepository playerTagRepository) {
        this.tagRepository = tagRepository;
        this.playerTagRepository = playerTagRepository;
    }

    @Override
    public List<Long> findPlayersByTag(String tagName) {
        Tag loadedTag = tagRepository.findOneByName(tagName);

        if (isNull(loadedTag)) {
            return emptyList();
        }
        return playerTagRepository.findOne(loadedTag.getId())
                .getPlayerIds()
                .stream()
                .collect(toList());
    }

    @Override
    public void markPayersWithTag(List<Long> playerIds, String tagName) {
        Tag tag = getTagByName(tagName);

        PlayerTag playerTag = playerTagRepository.findOne(tag.getId());

        if (isNull(playerTag)) {
            playerTagRepository.save(
                    new PlayerTag(tag.getId(), playerIds.stream().collect(toSet()))
            );
        } else {
            playerTag.getPlayerIds()
                    .addAll(playerIds);
            playerTagRepository.save(playerTag);
        }
    }

    @Override
    public void removeTagFromPlayers(List<Long> playerIds, String tagName) {
        Tag loadedTag = tagRepository.findOneByName(tagName);
        if (nonNull(loadedTag)) {
            removeTag(playerIds, loadedTag);
        }
    }

    private void removeTag(List<Long> playerIds, Tag loadedTag) {
        PlayerTag playerTag = playerTagRepository.findOne(loadedTag.getId());

        playerTag.getPlayerIds()
                .removeAll(playerIds);
        playerTagRepository.save(playerTag);
    }

    private Tag getTagByName(String tagName) {
        Tag loadedTag = tagRepository.findOneByName(tagName);
        return isNull(loadedTag) ? tagRepository.save(new Tag(tagName)) : loadedTag;
    }
}

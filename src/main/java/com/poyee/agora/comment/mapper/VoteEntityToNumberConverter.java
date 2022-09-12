package com.poyee.agora.comment.mapper;

import com.poyee.agora.entity.Vote;
import com.poyee.agora.entity.VoteId;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class VoteEntityToNumberConverter extends AbstractConverter<List<Vote>, List<Integer>> {
    @Override
    protected List<Integer> convert(List<Vote> source) {
        return source.stream()
                .map(Vote::getId)
                .map(VoteId::getNumber)
                .collect(Collectors.toList());
    }
}

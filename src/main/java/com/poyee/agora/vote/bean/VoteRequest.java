package com.poyee.agora.vote.bean;

import lombok.Data;

import java.util.List;

@Data
public class VoteRequest {
    Long pollId;
    List<Integer> optionNumbers;
}

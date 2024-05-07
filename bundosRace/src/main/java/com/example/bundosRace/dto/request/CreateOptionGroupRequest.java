package com.example.bundosRace.dto.request;


import com.example.bundosRace.domain.OptionGroup;

import java.util.List;

public record CreateOptionGroupRequest(
        Boolean necessary,
        String name,
        List<CreateOptionRequest> options
) {
    public OptionGroup toEntity() {
        return OptionGroup.builder()
                .necessary(necessary)
                .name(name)
                .build();
    }
}
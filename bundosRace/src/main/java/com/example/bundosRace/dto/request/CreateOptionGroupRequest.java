package com.example.bundosRace.dto.request;


import com.example.bundosRace.domain.OptionGroup;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOptionGroupRequest(
        @NotNull(message = "necessary 파라미터가 누락 되었습니다.")
        Boolean necessary,
        @NotNull(message = "name 파라미터가 누락 되었습니다.")
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
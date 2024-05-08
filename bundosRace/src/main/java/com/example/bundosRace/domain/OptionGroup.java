package com.example.bundosRace.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "option_groups")
public class OptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_group_id")
    private Long id;

    @Column(name = "option_necessary")
    private Boolean necessary;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder.Default
    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public void addOption(Option option) {
        options.add(option);
        option.setOptionGroup(this);
    }

    public void removeOption(Option option) {
        options.remove(option);
        option.setOptionGroup(null);
    }

    public void sellOption(Long optionId, int amount) {
        Option option = options.stream()
                .filter(o -> o.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션이 존재하지 않습니다."));
        option.sell(amount);

    }
}

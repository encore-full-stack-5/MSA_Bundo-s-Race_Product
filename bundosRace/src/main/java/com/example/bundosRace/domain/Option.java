package com.example.bundosRace.domain;


import com.example.bundosRace.core.error.ExpectedError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    @Column(name = "option_name")
    private String name;

    @Column(name = "option_price")
    private Integer price;

    @Column(name = "amount")
    private Long amount;

    public void sell(int amount) {
        if (this.amount < amount) {
            throw new ExpectedError.ResourceNotFoundException("옵션 "+ name +" 재고가 부족합니다.");
        }
        this.amount -= amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void checkAmount(int amount) {
        if (this.amount < amount) {
            throw new ExpectedError.ResourceNotFoundException("옵션 "+ name +" 재고가 부족합니다.");
        }
    }
}

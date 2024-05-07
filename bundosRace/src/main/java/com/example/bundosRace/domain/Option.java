package com.example.bundosRace.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    @Column(name = "option_name")
    private String name;

    @Column(name = "option_price")
    private Integer price;

    @Column(name = "option_soldout")
    private Long soldOut;

    @Column(name = "amount")
    private Long amount;

}

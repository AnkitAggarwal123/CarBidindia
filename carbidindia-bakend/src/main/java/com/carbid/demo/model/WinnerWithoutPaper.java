package com.carbid.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WinnerWithoutPaper extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "bidWithout_id")
    private BidWithoutPaper bidWithoutPaper;

    @OneToOne
    @JoinColumn(name = "car_id", unique = true)
    private Car car;

}

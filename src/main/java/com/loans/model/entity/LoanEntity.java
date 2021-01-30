package com.loans.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "LOAN")
public class LoanEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence_loan_id", sequenceName = "sequence_loan_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence_loan_id")
    @Column(name = "ID")
    private Long id;
    @Column(name = "TOTAL")
    private BigDecimal total;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    private UserEntity userId;

}
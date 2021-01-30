package com.loans.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "USER")
public class UserEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "sequence_user_id", sequenceName = "sequence_user_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence_user_id")
    @Column(name = "ID")
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @ToString.Exclude
    @OneToMany(mappedBy = "userId", fetch =  FetchType.LAZY, orphanRemoval = true, cascade=CascadeType.MERGE)
    private Set<LoanEntity> loans;

}
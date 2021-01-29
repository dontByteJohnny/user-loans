package com.loans.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade=CascadeType.PERSIST)
    private Set<LoanEntity> loans;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "-" + getId();
    }

}
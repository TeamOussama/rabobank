package com.rabobank.socle.data.entity;


import com.rabobank.socle.data.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Builder
@Table(name = "statement_record")
public class StatementRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4859325324422301664L;

    @Column(name = "transaction_reference")
    private String transactionReference;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "mutation")
    private float mutation;
    @Column(name = "start_balance")
    private float startBalance;
    @Column(name = "end_balance")
    private float endBalance;
    @Column(name = "description")
    private String description;
}

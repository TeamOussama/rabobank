package com.rabobank.socle.network.entity;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StatementRecordDTO {
    // TODO : add validation
    @NotEmpty
    @NotNull
    private String transactionReference;

    @NotEmpty
    @NotNull
    private String accountNumber;
    private float mutation;
    private float startBalance;
    private float endBalance;

    private String description;
}

package com.rabobank.socle.network.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRecord {

    private String reference;
    private String accountNumber;
}

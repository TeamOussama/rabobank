package com.rabobank.socle.network.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHttp {

    private String result;
    private ArrayList<ErrorRecord> errorRecords;
}

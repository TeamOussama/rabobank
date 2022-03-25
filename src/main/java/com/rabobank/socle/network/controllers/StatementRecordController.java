package com.rabobank.socle.network.controllers;


import com.rabobank.socle.network.entity.ResponseHttp;
import com.rabobank.socle.network.entity.StatementRecordDTO;
import com.rabobank.socle.service.StatementRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for customer statement records.
 */
@Controller
@RequestMapping("/statementRecords")
public class StatementRecordController {

    private StatementRecordService statementRecordService;

    @Autowired
    public StatementRecordController(StatementRecordService statementRecordService) {
        this.statementRecordService = statementRecordService;
    }

    /**
     * Create statementRecord.
     *
     * @param statementRecordDTO new statement record data
     * @return created statementRecord
     */
    @PostMapping("")
    @Transactional
    public ResponseEntity<ResponseHttp> addCustomerStatementRecord(
        @Valid @RequestBody StatementRecordDTO statementRecordDTO) {
        ResponseHttp responseHttp = this.statementRecordService.addCustomerStatementRecord(statementRecordDTO);
        return ok(responseHttp);
    }
}

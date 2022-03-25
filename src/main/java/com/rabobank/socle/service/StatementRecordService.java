package com.rabobank.socle.service;


import com.rabobank.socle.common.StatementErrorMessage;
import com.rabobank.socle.data.entity.StatementRecord;
import com.rabobank.socle.data.repository.StatementRecordRepository;
import com.rabobank.socle.network.entity.ErrorRecord;
import com.rabobank.socle.network.entity.ResponseHttp;
import com.rabobank.socle.network.entity.StatementRecordDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@SuppressWarnings({"checkstyle:ParameterNumber"})
public class StatementRecordService extends EntityBaseService<StatementRecord> {

    private final ModelMapper modelMapper;
    private final StatementRecordRepository statementRecordRepository;
    //private final StatementRecordRepository statementRecordRepository;

    @Autowired
    public StatementRecordService(StatementRecordRepository statementRecordRepository,
                                  ModelMapper modelMapper) {
        super(StatementRecord.class, statementRecordRepository);
        this.modelMapper = modelMapper;
        this.statementRecordRepository = statementRecordRepository;
    }

    public ResponseHttp addCustomerStatementRecord(StatementRecordDTO statementRecordDTO) {
        StatementRecord statementRecord = modelMapper.map(statementRecordDTO, StatementRecord.class);

        boolean isBalanceNotValide = this.isBalanceNotValide(statementRecord.getStartBalance(), statementRecord.getEndBalance(),
            statementRecord.getMutation());
        boolean isReferenceAlreadyExist = this.isReferenceAlreadyExist(statementRecord.getTransactionReference());

        return this.validateAndSaveStatement(statementRecord, isReferenceAlreadyExist, isBalanceNotValide);
    }

    public boolean isReferenceAlreadyExist(String reference) {
        return statementRecordRepository.findByTransactionReference(reference).isPresent();
    }

    public boolean isBalanceNotValide(float startBalance, float endBalance, float mutation) {
        float calculatedBalance = startBalance > endBalance ? startBalance - mutation : startBalance + mutation;
        return calculatedBalance != endBalance;
    }

    public ResponseHttp validateAndSaveStatement(StatementRecord statementRecord, boolean isReferenceAlreadyExist, boolean isBalanceNotValide) {
        ArrayList<ErrorRecord> errorRecords = new ArrayList<>();
        String responseMessage = "";

        if(isReferenceAlreadyExist && !isBalanceNotValide) {
            responseMessage = StatementErrorMessage.DUPLICATE_REFERENCE.name();
            errorRecords.add(new ErrorRecord(statementRecord.getTransactionReference(),"account_number_of_error_record"));
        }

        if(!isReferenceAlreadyExist && isBalanceNotValide) {
            responseMessage = StatementErrorMessage.INCORRECT_END_BALANCE.name();
            errorRecords.add(new ErrorRecord(statementRecord.getTransactionReference(),"account_number_of_error_record"));
        }

        if(isReferenceAlreadyExist && isBalanceNotValide) {
            responseMessage = StatementErrorMessage.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.name();
            errorRecords.add(new ErrorRecord(statementRecord.getTransactionReference(),"account_number_of_duplicate_record"));
            errorRecords.add(new ErrorRecord(statementRecord.getTransactionReference(),"account_number_of_ inCorrectEndBalance _record"));
        }

        if(!isReferenceAlreadyExist && !isBalanceNotValide) {
            responseMessage = StatementErrorMessage.SUCCESSFUL.name();
            statementRecordRepository.save(statementRecord);
        }

        return new ResponseHttp(responseMessage, errorRecords);
    }
}

package com.rabobank.socle.unit;

import com.rabobank.socle.authentication.entity.SignUpDTO;
import com.rabobank.socle.common.StatementErrorMessage;
import com.rabobank.socle.common.Utilities;
import com.rabobank.socle.data.entity.StatementRecord;
import com.rabobank.socle.data.entity.User;
import com.rabobank.socle.data.repository.StatementRecordRepository;
import com.rabobank.socle.network.entity.ResponseHttp;
import com.rabobank.socle.network.entity.StatementRecordDTO;
import com.rabobank.socle.service.StatementRecordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatementRecordServiceUnitTest {
    @Spy
    @InjectMocks
    private StatementRecordService statementRecordService;

    @Mock
    private StatementRecordRepository statementRecordRepository;

    @Mock
    private ModelMapper modelMapper;

    private StatementRecordDTO statementRecordDTO;
    private StatementRecord statementRecord;
    Optional<StatementRecord> optionalStatement;
    private SignUpDTO signUpDTOWithAgeLessThan18;
    private User expectedUser;

    Optional<StatementRecord> emptyOptionalStatement = Optional.empty();

@Autowired
    private   Utilities utilities;

    @Before
    public void Before() {

        statementRecordDTO = StatementRecordDTO.builder()
            .transactionReference("Htitioussama@gmail.com")
            .accountNumber("Oussama htiti")
            .mutation(700)
            .startBalance(1000)
            .endBalance(300)
            .description("description")
            .build();

        statementRecord = StatementRecord.builder()
            .transactionReference("Htitioussama@gmail.com")
            .accountNumber("Oussama htiti")
            .mutation(700)
            .startBalance(1000)
            .endBalance(300)
            .description("description")
            .build();

        optionalStatement = Optional.of(statementRecord);

        signUpDTOWithAgeLessThan18 = SignUpDTO.builder()
            .email("Htitioussama@gmail.com")
            .fullName("Oussama htiti")
            .age(15)
            .countryCode("fr")
            .password("isASecret")
            .confirmPassword("isASecret")
            .build();

        expectedUser = User.builder()
            .email("Htitioussama@gmail.com")
            .age(29)
            .build();

    }

    @Test
    public void addCustomerStatementRecord_Success() {
        when(statementRecordRepository.findByTransactionReference("exist_reference")).thenReturn(optionalStatement);
        when(statementRecordRepository.findByTransactionReference("does_not_exist_reference")).thenReturn(emptyOptionalStatement);
        when(modelMapper.map(statementRecordDTO, StatementRecord.class)).thenReturn(statementRecord);

        statementRecordService.addCustomerStatementRecord(statementRecordDTO);

        verify(statementRecordService, times(1)).isBalanceNotValide(statementRecord.getStartBalance(),statementRecord.getEndBalance(), statementRecord.getMutation());
        verify(statementRecordService, times(1)).isReferenceAlreadyExist(statementRecord.getTransactionReference());
        verify(statementRecordService, times(1)).validateAndSaveStatement(statementRecord,false,false);
    }

    @Test
    public void isReferenceAlreadyExist_Success() {
        when(statementRecordRepository.findByTransactionReference("exist_reference")).thenReturn(optionalStatement);
        when(statementRecordRepository.findByTransactionReference("does_not_exist_reference")).thenReturn(emptyOptionalStatement);

        assertEquals(statementRecordService.isReferenceAlreadyExist("exist_reference"), true);
        assertEquals(statementRecordService.isReferenceAlreadyExist("does_not_exist_reference"), false);
    }

    @Test
    public void calculateBalance_Success() {
        assertEquals(statementRecordService.isBalanceNotValide(1000,300, 700), false);
        assertEquals(statementRecordService.isBalanceNotValide(1000,3000, 2000), false);
        assertEquals(statementRecordService.isBalanceNotValide(1000,5000, 2000), true);
        assertEquals(statementRecordService.isBalanceNotValide(1000,200, 300), true);
    }

    @Test
    public void validateAndSaveStatement_DUPLICATE_REFERENCE_INCORRECT_END_BALANCE_Success() {
        ResponseHttp responseHttp = statementRecordService.validateAndSaveStatement(statementRecord, true, true);

        assertEquals(responseHttp.getResult(), StatementErrorMessage.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.name());
        assertEquals(responseHttp.getErrorRecords().size(), 2);
    }

    @Test
    public void validateAndSaveStatement_SUCCESSFULS_Success() {
        ResponseHttp responseHttp = statementRecordService.validateAndSaveStatement(statementRecord, false, false);

        assertEquals(responseHttp.getResult(), StatementErrorMessage.SUCCESSFUL.name());
        assertEquals(responseHttp.getErrorRecords().size(), 0);
    }

    @Test
    public void validateAndSaveStatement_DUPLICATE_REFERENCE_Success() {
        ResponseHttp responseHttp = statementRecordService.validateAndSaveStatement(statementRecord, true, false);

        assertEquals(responseHttp.getResult(), StatementErrorMessage.DUPLICATE_REFERENCE.name());
        assertEquals(responseHttp.getErrorRecords().size(), 1);
    }

    @Test
    public void validateAndSaveStatement_INCORRECT_END_BALANCE_Success() {
        ResponseHttp responseHttp = statementRecordService.validateAndSaveStatement(statementRecord, false, true);

        assertEquals(responseHttp.getResult(), StatementErrorMessage.INCORRECT_END_BALANCE.name());
        assertEquals(responseHttp.getErrorRecords().size(), 1);
    }
}

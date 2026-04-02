package com.pos.main.api.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.pos.main.acc.entity.AccCoa;
import com.pos.main.acc.entity.AccLedger;
import com.pos.main.acc.entity.AccPeriod;
import com.pos.main.acc.repository.AccLedgerRepository;
import com.pos.main.acc.repository.AccPeriodRepository;
import com.pos.main.api.dto.BankRequestDto;
import com.pos.main.api.dto.BankResponse;
import com.pos.main.api.dto.CacheData;
import com.pos.main.approval.entity.ApprovalGroup;
import com.pos.main.approval.entity.ApprovalStatus;
import com.pos.main.clearing.entity.PosMachine;
import com.pos.main.clearing.repository.PosMachineRepository;
import com.pos.main.exception.BankApiException;
import com.pos.main.pos.entity.Plazas;
import com.pos.main.pos.repository.PlazasRepository;
import com.pos.main.remit.entity.BankApiHoRemittance;
import com.pos.main.remit.entity.RemitBank;
import com.pos.main.remit.entity.RemitBankAccount;
import com.pos.main.remit.repository.BankApiHoRemittanceRepository;
import com.pos.main.remit.repository.RemitBankAccountRepository;
import com.pos.main.remit.repository.RemitBankRepository;
import com.pos.main.utils.service.HmacService;
import com.pos.main.utils.service.UtilityService;

@Service
public class HeadOfficeRemittanceService {

    private final BankApiHoRemittanceRepository bankApiHoRemittanceRepo;
	private final PlazasRepository plazasRepo;
	private final RemitBankAccountRepository remitBankAccountRepo;
	private final RemitBankRepository remitBankRepo;
	private final AccLedgerRepository accLedgerRepo;
	private final PosMachineRepository posMachineRepo;
	private final HmacService hmacService;
	private final UtilityService utilityService;
	private final CacheData cacheData;
	private final AccPeriodRepository accPeriodRepo;
	private static final String SYSTEM_USER = "AUTO_BANK_API";
	private static final String GROUP_CLEARING = "CLEARING_INSPECTOR";
	private static final String STATUS_SUBMITTED = "SUBMITTED";
	private static final String STATUS_CLEARED = "CLEARED";
	

	public HeadOfficeRemittanceService(BankApiHoRemittanceRepository bankApiHoRemittanceRepo,
			PlazasRepository plazasRepo, RemitBankAccountRepository remitBankAccountRepo,
			RemitBankRepository remitBankRepo, AccLedgerRepository accLedgerRepo, PosMachineRepository posMachineRepo,
			HmacService hmacService, UtilityService utilityService, CacheData cacheData,
			AccPeriodRepository accPeriodRepo) {
		super();
		this.bankApiHoRemittanceRepo = bankApiHoRemittanceRepo;
		this.plazasRepo = plazasRepo;
		this.remitBankAccountRepo = remitBankAccountRepo;
		this.remitBankRepo = remitBankRepo;
		this.accLedgerRepo = accLedgerRepo;
		this.posMachineRepo = posMachineRepo;
		this.hmacService = hmacService;
		this.utilityService = utilityService;
		this.cacheData = cacheData;
		this.accPeriodRepo = accPeriodRepo;
	}


	@Transactional
	public BankResponse remitAutomation(BankRequestDto request, String rawJson, String signature) {
		
		
	    	if (!hmacService.verifySignature(rawJson, signature)) {
                throw new BankApiException("Invalid Signature!", 401, request.getRequestId());
            }
		
			
			if (bankApiHoRemittanceRepo.existsByRequestId(request.getRequestId())) {
				throw new BankApiException("Transaction already exists!", 409, request.getRequestId());
			}
			
			Plazas plazaInstance = plazasRepo.findByVamCode(request.getVamCode())
			        .orElseThrow(() -> new BankApiException(
			                "Plaza not found for VAM Code: " + request.getVamCode(),
			                404,
			                request.getRequestId()
			        ));

			RemitBankAccount remitBankAccountInstance = remitBankAccountRepo
			        .findByAccountNo(request.getRemitBankAccount())
			        .orElseThrow(() -> new BankApiException(
			                "Remit Bank Account not found!",
			                404,
			                request.getRequestId()
			        ));
                   
			RemitBank remitBankInstance = remitBankRepo
			        .findByBankName(request.getRemitBank())
			        .orElseThrow(() -> new BankApiException(
			                "Remit Bank not found!",
			                404,
			                request.getRequestId()
			        ));
			
			
			ApprovalGroup approvalGroupInstance = cacheData.getGroup(GROUP_CLEARING);

			ApprovalStatus prevStatusInstance = cacheData.getStatus(STATUS_SUBMITTED);
			ApprovalStatus curStatusInstance = cacheData.getStatus(STATUS_CLEARED);


			BankApiHoRemittance remittance=new BankApiHoRemittance();
			
			Optional<PosMachine> remitBankMatch =
			        posMachineRepo.findByPlazaAndRemitBank(plazaInstance, remitBankInstance);

			if (remitBankMatch.isPresent() && remitBankMatch.get().getStoreId() != null) {
				remittance.setMatchingKeyword(remitBankMatch.get().getStoreId());
			}
			
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDateTime cur = LocalDateTime.now();
			LocalDate remitDate;
		    try {
		        remitDate = LocalDate.parse(request.getRemitDate(), df);
		    } catch (Exception e) {
		        throw new BankApiException("Invalid date format! Expected dd/MM/yyyy", 400, request.getRequestId());
		    }
		    remittance.setRemitDate(remitDate.atStartOfDay());

			String code=utilityService.getCode("BankApiHoRemittance", "HP", plazaInstance.getId(), 8);
			remittance.setCode(code);
	        remittance.setPlaza(plazaInstance);
	        remittance.setRemitBankAccount(remitBankAccountInstance);
	        remittance.setRemitBank(remitBankInstance);
	        remittance.setRemitBankBranch(request.getRemitBankBranch());
	        remittance.setRemitAmount(request.getRemitAmount());
	        remittance.setRemitMode(request.getRemitMode());
	        remittance.setRemitRefNo(request.getRemitRefNo());
	        remittance.setBankReceiptNo(request.getBankReceiptNo());
	        remittance.setRemarks(request.getRemarks());
	        remittance.setRequestId(request.getRequestId());

	        remittance.setCreatedBy(SYSTEM_USER);
	        remittance.setCreatedDate(cur);
	        remittance.setClearedBy(SYSTEM_USER);
	        remittance.setClearedDate(cur);
	        remittance.setIsCleared(true);
	        
	        remittance.setPrevStatus(prevStatusInstance);
	        remittance.setCurrStatus(curStatusInstance);
	        remittance.setCurrentServeGroup(approvalGroupInstance);
	        bankApiHoRemittanceRepo.save(remittance);
	        
	        // accounting
	        AccCoa accClearing = cacheData.getAcccoa("BANK_CLEARING_AC");
	        AccCoa accCash = cacheData.getAcccoa("CASH_IN_HAND");
	        AccCoa accBank = cacheData.getAcccoa("CASH_AT_BANK");
	        
	        LocalDate currDate = LocalDate.now();

	        AccPeriod accPeriod = accPeriodRepo
	        	    .findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDesc(currDate, currDate)
	        	    .orElseThrow(() -> new BankApiException(
	        	        "Active accounting period not found",
	        	        500,
	        	        request.getRequestId()
	        	    ));
	        
	        // Bank Clearing A/C Debit
	        AccLedger accLedgerInstance1=new AccLedger();
	        accLedgerInstance1.setPlazas(plazaInstance);
	        accLedgerInstance1.setPostDate(cur.toLocalDate());
	        accLedgerInstance1.setGlDate(remitDate);
	        accLedgerInstance1.setAccCoaLedgerIdAccCoa(accClearing);
	        accLedgerInstance1.setAccCoaRefIdAccCoa(accCash);
	        accLedgerInstance1.setAccPeriodIdAccPeriod(accPeriod);
	        accLedgerInstance1.setDebitAmount(request.getRemitAmount());
	        accLedgerInstance1.setCreditAmount(BigDecimal.ZERO);
	        accLedgerInstance1.setNarration("AUTO BANK CLEARING AC DEBITED");
	        accLedgerInstance1.setParticulars(remittance.getRemitBank().getBankName());
	        accLedgerInstance1.setVoucherNo(remittance.getCode());
	        accLedgerInstance1.setVoucherType("Head Office Payment");
	        accLedgerInstance1.setRefNo(remittance.getId().toString());
	        accLedgerInstance1.setRefSource("REMIT_HO_REMITTANCE");
	        accLedgerInstance1.setRemitBank(remittance.getRemitBank());
	        
	        // Cash Credit
	        AccLedger accLedgerInstance2=new AccLedger();
	        accLedgerInstance2.setPlazas(plazaInstance);
	        accLedgerInstance2.setPostDate(cur.toLocalDate());
	        accLedgerInstance2.setGlDate(remitDate);
	        accLedgerInstance2.setAccCoaLedgerIdAccCoa(accCash);
	        accLedgerInstance2.setAccCoaRefIdAccCoa(accClearing);
	        accLedgerInstance2.setAccPeriodIdAccPeriod(accPeriod);
	        accLedgerInstance2.setDebitAmount(BigDecimal.ZERO);
	        accLedgerInstance2.setCreditAmount(request.getRemitAmount());
	        accLedgerInstance2.setRemitBank(remittance.getRemitBank());
	        accLedgerInstance2.setNarration("AUTO CASH CREDITED");
	        accLedgerInstance2.setParticulars(remittance.getRemitBank().getBankName());
	        accLedgerInstance2.setVoucherNo(remittance.getCode());
	        accLedgerInstance2.setVoucherType("Head Office Payment");
	        accLedgerInstance2.setRefNo(remittance.getId().toString());
	        accLedgerInstance2.setRefSource("REMIT_HO_REMITTANCE");
	        
	        
	        // Bank Clearing A/C Credit
	        AccLedger accLedgerInstance3=new AccLedger();
	        accLedgerInstance3.setPlazas(plazaInstance);
	        accLedgerInstance3.setPostDate(cur.toLocalDate());
	        accLedgerInstance3.setGlDate(remitDate);
	        accLedgerInstance3.setAccCoaLedgerIdAccCoa(accClearing);
	        accLedgerInstance3.setAccCoaRefIdAccCoa(accBank);
	        accLedgerInstance3.setAccPeriodIdAccPeriod(accPeriod);
	        accLedgerInstance3.setDebitAmount(BigDecimal.ZERO);
	        accLedgerInstance3.setCreditAmount(request.getRemitAmount());
	        accLedgerInstance3.setNarration("AUTO BANK CLEARING AC CREDITED");
	        accLedgerInstance3.setParticulars(remittance.getRemitBank().getBankName());
	        accLedgerInstance3.setVoucherNo(remittance.getCode());
	        accLedgerInstance3.setVoucherType("Head Office Payment");
	        accLedgerInstance3.setRefNo(remittance.getId().toString());
	        accLedgerInstance3.setRefSource("REMIT_HO_REMITTANCE");
	        accLedgerInstance3.setRemitBank(remittance.getRemitBank());
	        
	        // Cash at Bank Debit
	        AccLedger accLedgerInstance4=new AccLedger();
	        accLedgerInstance4.setPlazas(plazaInstance);
	        accLedgerInstance4.setPostDate(cur.toLocalDate());
	        accLedgerInstance4.setGlDate(remitDate);
	        accLedgerInstance4.setAccCoaLedgerIdAccCoa(accBank);
	        accLedgerInstance4.setAccCoaRefIdAccCoa(accClearing);
	        accLedgerInstance4.setAccPeriodIdAccPeriod(accPeriod);
	        accLedgerInstance4.setDebitAmount(request.getRemitAmount());
	        accLedgerInstance4.setCreditAmount(BigDecimal.ZERO);
	        accLedgerInstance4.setRemitBank(remittance.getRemitBank());
	        accLedgerInstance4.setNarration("Head Office Payment");
	        accLedgerInstance4.setParticulars(remittance.getRemitBank().getBankName());
	        accLedgerInstance4.setVoucherNo(remittance.getCode());
	        accLedgerInstance4.setVoucherType("Head Office Payment");
	        accLedgerInstance4.setRefNo(remittance.getId().toString());
	        accLedgerInstance4.setRefSource("REMIT_HO_REMITTANCE");
	       
	        accLedgerRepo.saveAll(Arrays.asList(accLedgerInstance1,accLedgerInstance2,accLedgerInstance3,accLedgerInstance4));
	        
	        return new BankResponse(200, request.getRequestId(),"Success");
		 
	}
}

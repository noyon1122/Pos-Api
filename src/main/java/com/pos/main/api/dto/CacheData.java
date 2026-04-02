package com.pos.main.api.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.pos.main.acc.entity.AccCoa;
import com.pos.main.acc.repository.AccCoaRepository;
import com.pos.main.approval.entity.ApprovalGroup;
import com.pos.main.approval.entity.ApprovalStatus;
import com.pos.main.approval.repository.ApprovalGroupRepository;
import com.pos.main.approval.repository.ApprovalStatusRepository;
@Component
public class CacheData {

	private final ApprovalGroupRepository approvalGroupRepo;
    private final ApprovalStatusRepository approvalStatusRepo;
    private final AccCoaRepository accCoaRepo;

    private Map<String, ApprovalGroup> groupCache = new HashMap<>();
    private Map<String, ApprovalStatus> statusCache = new HashMap<>();
    private Map<String, AccCoa> acccoaCache = new HashMap<>();


    public CacheData(ApprovalGroupRepository approvalGroupRepo, ApprovalStatusRepository approvalStatusRepo,
			AccCoaRepository accCoaRepo) {
		super();
		this.approvalGroupRepo = approvalGroupRepo;
		this.approvalStatusRepo = approvalStatusRepo;
		this.accCoaRepo = accCoaRepo;
	}

	@PostConstruct
    public void init() {

    	// ✅ Load only required ApprovalGroup
    	List<ApprovalGroup> groups = approvalGroupRepo
    	        .findByKeywordIn(Arrays.asList("CLEARING_INSPECTOR"));

    	groups.forEach(g -> groupCache.put(g.getKeyword(), g));

    	// ✅ Load only required ApprovalStatus
    	List<ApprovalStatus> statuses = approvalStatusRepo
    	        .findByKeywordIn(Arrays.asList("SUBMITTED", "CLEARED"));

    	statuses.forEach(s -> statusCache.put(s.getKeyword(), s));
    	
    	// ✅ Load only required AccCoa
    	List<AccCoa> acccoas = accCoaRepo
    	        .findByKeywordIn(Arrays.asList("BANK_CLEARING_AC", "CASH_IN_HAND","CASH_AT_BANK"));

    	acccoas.forEach(a -> acccoaCache.put(a.getKeyword(), a));
    }

    public ApprovalGroup getGroup(String keyword) {
        return groupCache.get(keyword);
    }

    public ApprovalStatus getStatus(String keyword) {
        return statusCache.get(keyword);
    }
    
    public AccCoa getAcccoa(String keyword) {
        return acccoaCache.get(keyword);
    }
}

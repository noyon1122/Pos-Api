package com.pos.main.utils.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pos.main.utils.entity.AllCodeDef;
import com.pos.main.utils.repository.AllCodeDefRepository;

@Service
public class UtilityService {

	private final AllCodeDefRepository allCodeDefRepo;

	public UtilityService(AllCodeDefRepository allCodeDefRepo) {
		super();
		this.allCodeDefRepo = allCodeDefRepo;
	}
	
	@Transactional
    public String getCode(String domainName, String prefix, Integer plazaId, Integer cdLength) {

        Optional<AllCodeDef> optional;

        if (plazaId == null) {
            optional = allCodeDefRepo
                    .findByPrefixAndPojoClass(prefix, domainName);
        } else {
            optional = allCodeDefRepo
                    .findByPrefixAndPojoClassAndPlazaId(prefix, domainName, plazaId);
        }

        AllCodeDef allCodeDef;
        int nxtVal = 1;

        if (optional.isPresent()) {
            allCodeDef = optional.get();
            nxtVal = allCodeDef.getNxtVal();
        } else {
            allCodeDef = new AllCodeDef();
            allCodeDef.setPrefix(prefix);
            allCodeDef.setCdLength(cdLength);
            allCodeDef.setPojoClass(domainName);
            allCodeDef.setCode(domainName);
            allCodeDef.setPlazaId(plazaId);
            nxtVal = 1;
        }

        // Generate code with leading zero padding
        String formattedNumber = String.format("%0" + cdLength + "d", nxtVal);
        String lastCodeVal = prefix + formattedNumber;

        // Increment next value
        allCodeDef.setNxtVal(nxtVal + 1);

        // Save
        allCodeDefRepo.save(allCodeDef);

        return lastCodeVal;
    }
}

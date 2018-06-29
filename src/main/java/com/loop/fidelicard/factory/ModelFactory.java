package com.loop.fidelicard.factory;

import com.loop.fidelicard.dto.DefaultDTO;
import com.loop.fidelicard.dto.enterprise.EnterpriseDTO;
import com.loop.fidelicard.model.DefaultModel;
import com.loop.fidelicard.model.Enterprise;

public class ModelFactory {

	public static DefaultModel get(DefaultDTO defaultDTO) {
		if (defaultDTO instanceof EnterpriseDTO) {
			return new Enterprise((EnterpriseDTO) defaultDTO);
		}
		throw new IllegalArgumentException("Cast not found to " + defaultDTO);
	}

}

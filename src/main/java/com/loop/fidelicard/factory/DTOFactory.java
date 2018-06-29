package com.loop.fidelicard.factory;

import com.loop.fidelicard.dto.DefaultDTO;
import com.loop.fidelicard.dto.enterprise.ResponseEnterpriseDTO;
import com.loop.fidelicard.model.Enterprise;

public class DTOFactory {

	public static DefaultDTO get(Object e) {
		if (e instanceof Enterprise) {
			return new ResponseEnterpriseDTO((Enterprise) e);
		}
		try {
			throw new IllegalAccessException("class not found to Object " + e);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}

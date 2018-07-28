package com.dm.trade.common.controller;

import org.springframework.stereotype.Controller;
import com.dm.trade.common.utils.ShiroUtils;
import com.dm.trade.system.domain.UserDO;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}
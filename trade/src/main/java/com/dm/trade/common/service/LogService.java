package com.dm.trade.common.service;

import org.springframework.stereotype.Service;

import com.dm.trade.common.domain.LogDO;
import com.dm.trade.common.domain.PageDO;
import com.dm.trade.common.utils.Query;
@Service
public interface LogService {
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}

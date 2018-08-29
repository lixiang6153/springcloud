package com.easystudy.service;

import com.easystudy.model.Rights;

public interface RightService extends BaseService<Rights>{
	/**
	 * 通过权限值获取权限详情
	 * @param rightValue
	 * @return
	 */
	public Rights findRightsByValue(String rightValue);
}

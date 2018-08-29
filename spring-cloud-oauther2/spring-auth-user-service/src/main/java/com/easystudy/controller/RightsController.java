package com.easystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.Rights;
import com.easystudy.service.RightService;

@RestController
@RequestMapping("/rights")
public class RightsController {
	
	@Autowired
	protected RightService rightService;
	
	/**	
	 * 通过角色id获取角色权限列表
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/findByRightId/{rightId}", method = RequestMethod.GET)
	public ReturnValue<Rights> findByRightId(@PathVariable("rightId") Long rightId){
		Rights right = rightService.find(rightId);
		return new ReturnValue<Rights>(right);
	}
}

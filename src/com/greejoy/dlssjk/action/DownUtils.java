package com.greejoy.dlssjk.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.greejoy.rbac.domain.annotation.MenuMapping;

@Controller
public class DownUtils {

	@RequestMapping("/tools/download")
	@MenuMapping(name="相关工具",url="/tools/download",code="200009",parentCode="200000")
	public ModelAndView down(){
		return new ModelAndView("pages/dlssjk/tools");
	}
	
}

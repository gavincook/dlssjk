package com.greejoy.dlssjk.init;

import java.util.ArrayList;
import java.util.List;

import com.greejoy.rbac.domain.Menu;
import com.greejoy.rbac.domain.init.loader.MenuLoader;

public class MenuIniter implements MenuLoader{

	@Override
	public List<Menu> getMenus() {
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(new Menu("双视监控", "", "200000", null));
		return menus;
	}

}

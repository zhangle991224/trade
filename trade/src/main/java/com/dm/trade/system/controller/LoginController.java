package com.dm.trade.system.controller;

import com.dm.trade.common.annotation.Log;
import com.dm.trade.common.controller.BaseController;
import com.dm.trade.common.domain.Tree;
import com.dm.trade.common.utils.MD5Utils;
import com.dm.trade.common.utils.R;
import com.dm.trade.common.utils.ShiroUtils;
import com.dm.trade.system.domain.MenuDO;
import com.dm.trade.system.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;

	@GetMapping({ "/", "" })
	public String welcome(Model model) {
		return "redirect:/login";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	public String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@Log("请求访问手机主页")
	@GetMapping({ "/h5" })
	String index_1(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		model.addAttribute("username", getUser().getUsername());
		return "h5";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

	@GetMapping("/403")
	String error403() {
		return "403";
	}

}

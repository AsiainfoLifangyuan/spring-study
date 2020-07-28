package com.bsth.si.controller.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bsth.si.controller.BaseController;
import com.bsth.si.entity.Users;
import com.bsth.si.service.UsersService;
import com.bsth.si.util.ReMessage;

@Controller
@RequestMapping("/Users/*")
@SessionAttributes("currUser")
public class UsersController extends BaseController<Users> {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UsersService service;

	@Autowired
	public UsersController(UsersService service) {
		setBaseService(service);
	}

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(@RequestParam Map<String, Object> map) {
		logger.debug(map);
		ModelAndView andView = new ModelAndView();
		andView.addAllObjects(map);
		andView.setViewName("/pages/users/list.jsp");
		logger.debug(andView);
		return andView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam Map<String, Object> map) {
		logger.debug(map);
		ModelAndView andView = new ModelAndView();
		andView.addAllObjects(map);
		andView.setViewName("/pages/users/edit.jsp");
		logger.debug(andView);
		return andView;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> check(@RequestParam Map<String, Object> map) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ReMessage reMessage = new ReMessage();
		try {
			String checkId = (String) map.get("checkId");
			List<Users> checkList = service.query(map);
			int count = 0;
			if(checkId.equals("")){
				count= checkList.size();
			}else{
				for (Users temp : checkList) {
					if(!temp.getId().equals(Long.valueOf(checkId))){
						count++;
					}
				}
			}
			if(count>0){
				reMessage.setError(true);
				reMessage.setMessage("存在相同！");
			}
			
			modelMap.put("msg", reMessage);
		} catch (Exception e) {
			reMessage.isError = true;
			modelMap.put("msg", reMessage);
			e.printStackTrace();
		}
		return modelMap;
	}
	
	
}

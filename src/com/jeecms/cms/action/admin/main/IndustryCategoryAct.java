package com.jeecms.cms.action.admin.main;

import com.jeecms.cms.entity.main.IndustryCategory;
import com.jeecms.cms.manager.main.IndustryCategoryMng;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebCoreErrors;
import com.jeecms.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class IndustryCategoryAct {
	private static final Logger log = LoggerFactory.getLogger(IndustryCategoryAct.class);
	
	@RequiresPermissions("industryCategory:industryCategory_main")
	@RequestMapping("/industryCategory/industryCategory_main.do")
	public String industryCategoryMain(ModelMap model) {
		return "industrycategory/industryCategory_main";
	}
	
	@RequiresPermissions("industryCategory:v_left")
	@RequestMapping("/industryCategory/v_left.do")
	public String left() {
		return "industrycategory/left";
	}
	
	@RequiresPermissions("industryCategory:v_tree")
	@RequestMapping(value = "/industryCategory/v_tree.do")
	public String selectParent(String root, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		log.debug("tree path={}", root);
		boolean isRoot;
		// jquery treeview的根请求为root=source
		if (StringUtils.isBlank(root) || "source".equals(root)) {
			isRoot = true;
		} else {
			isRoot = false;
		}
		model.addAttribute("isRoot", isRoot);
		List<IndustryCategory> industryCategoryList;
		industryCategoryList= manager.getList(root);
		
		model.addAttribute("list", industryCategoryList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "industrycategory/tree";
	}
	
	@RequiresPermissions("industryCategory:v_list")
	@RequestMapping("/industryCategory/v_list.do")
	public String list(HttpServletRequest request,
					   ModelMap model, String root) {
		List<IndustryCategory> list;
		list = manager.getList(root);
		model.addAttribute("list", list);
		model.addAttribute("root", root);
		return "industrycategory/list";
	}

	@RequiresPermissions("industryCategory:v_add")
	@RequestMapping("/industryCategory/v_add.do")
	public String add(Integer root,ModelMap model) {
		IndustryCategory industryCategory =null;
		if(root!=null){
			industryCategory= manager.findById(root);

		}else {
			 industryCategory = new IndustryCategory();
			IndustryCategory parent = new IndustryCategory();
			industryCategory.setParent(parent);
		}
		model.addAttribute("industryCategory", industryCategory);
		model.addAttribute("root", root);
		return "industrycategory/add";
	}

	@RequiresPermissions("industryCategory:v_edit")
	@RequestMapping("/industryCategory/v_edit.do")
	public String edit(Integer id,HttpServletRequest request, ModelMap model) {
		WebCoreErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("industryCategory", manager.findById(id));
		return "industrycategory/edit";
	}

	@RequiresPermissions("industryCategory:o_save")
	@RequestMapping("/industryCategory/o_save.do")
	public String save(IndustryCategory bean, Integer pid,
			HttpServletRequest request, ModelMap model) throws IOException {
		CmsUser user = CmsUtils.getUser(request);
		WebCoreErrors errors = validateSave(bean, null, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean,pid );
		log.info("save IndustryCategory id={}", bean.getId());
		cmsLogMng.operating(request, "tgIndustryCategory.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do?root="+(pid==null?"":pid);
	}

	@RequiresPermissions("industryCategory:o_update")
	@RequestMapping("/industryCategory/o_update.do")
	public String update(IndustryCategory bean, Integer uploadFtpId,
			Integer syncPageFtpId, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebCoreErrors errors = validateUpdate(bean.getId(), uploadFtpId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update IndustryCategory id={}.", bean.getId());
		cmsLogMng.operating(request, "tgIndustryCategory.log.update", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return list(request, model, null);
	}

	@RequiresPermissions("industryCategory:o_delete")
	@RequestMapping("/industryCategory/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebCoreErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		IndustryCategory[] beans = manager.deleteByIds(ids);
		for (IndustryCategory bean : beans) {
			log.info("delete IndustryCategory id={}", bean.getId());
			cmsLogMng.operating(request, "tgIndustryCategory.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model, null);
	}


	private WebCoreErrors validateSave(IndustryCategory bean, Integer uploadFtpId,
			HttpServletRequest request) {
		WebCoreErrors errors = WebCoreErrors.create(request);

		return errors;
	}

	private WebCoreErrors validateEdit(Integer id, HttpServletRequest request) {
		WebCoreErrors errors = WebCoreErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebCoreErrors validateUpdate(Integer id, Integer uploadFtpId,
			HttpServletRequest request) {
		WebCoreErrors errors = WebCoreErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebCoreErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebCoreErrors errors = WebCoreErrors.create(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}



	private boolean vldExist(Integer id, WebCoreErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		IndustryCategory entity = manager.findById(id);
		if (errors.ifNotExist(entity, IndustryCategory.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private IndustryCategoryMng manager;
}
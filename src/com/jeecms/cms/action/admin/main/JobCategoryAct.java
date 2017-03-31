package com.jeecms.cms.action.admin.main;

import com.jeecms.cms.entity.main.JobCategory;
import com.jeecms.cms.manager.main.JobCategoryMng;
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
public class JobCategoryAct {
	private static final Logger log = LoggerFactory.getLogger(JobCategoryAct.class);
	
	@RequiresPermissions("jobCategory:jobCategory_main")
	@RequestMapping("/jobCategory/jobCategory_main.do")
	public String jobCategoryMain(ModelMap model) {
		return "jobCategory/jobCategory_main";
	}
	
	@RequiresPermissions("jobCategory:v_left")
	@RequestMapping("/jobCategory/v_left.do")
	public String left() {
		return "jobCategory/left";
	}
	
	@RequiresPermissions("jobCategory:v_tree")
	@RequestMapping(value = "/jobCategory/v_tree.do")
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
		List<JobCategory> jobCategoryList;
		jobCategoryList= manager.getList(root);
		
		model.addAttribute("list", jobCategoryList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "jobCategory/tree";
	}
	
	@RequiresPermissions("jobCategory:v_list")
	@RequestMapping("/jobCategory/v_list.do")
	public String list(HttpServletRequest request,
					   ModelMap model, String root) {
		List<JobCategory> list;
		list = manager.getList(root);
		model.addAttribute("list", list);
		model.addAttribute("root", root);
		return "jobCategory/list";
	}

	@RequiresPermissions("jobCategory:v_add")
	@RequestMapping("/jobCategory/v_add.do")
	public String add(Integer root,ModelMap model) {
		JobCategory jobCategory =null;
		if(root!=null){
			jobCategory= manager.findById(root);

		}else {
			 jobCategory = new JobCategory();
			JobCategory parent = new JobCategory();
			jobCategory.setParent(parent);
		}
		model.addAttribute("jobCategory", jobCategory);
		model.addAttribute("root", root);
		return "jobCategory/add";
	}

	@RequiresPermissions("jobCategory:v_edit")
	@RequestMapping("/jobCategory/v_edit.do")
	public String edit(Integer id,HttpServletRequest request, ModelMap model) {
		WebCoreErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("tgJobCategory", manager.findById(id));
		return "jobCategory/edit";
	}

	@RequiresPermissions("jobCategory:o_save")
	@RequestMapping("/jobCategory/o_save.do")
	public String save(JobCategory bean, Integer pid,
			HttpServletRequest request, ModelMap model) throws IOException {
		CmsUser user = CmsUtils.getUser(request);
		WebCoreErrors errors = validateSave(bean, null, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean,pid );
		log.info("save JobCategory id={}", bean.getId());
		cmsLogMng.operating(request, "tgJobCategory.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do?root="+(pid==null?"":pid);
	}

	@RequiresPermissions("jobCategory:o_update")
	@RequestMapping("/jobCategory/o_update.do")
	public String update(JobCategory bean, Integer uploadFtpId,
			Integer syncPageFtpId, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebCoreErrors errors = validateUpdate(bean.getId(), uploadFtpId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update JobCategory id={}.", bean.getId());
		cmsLogMng.operating(request, "tgJobCategory.log.update", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return list(request, model, null);
	}

	@RequiresPermissions("jobCategory:o_delete")
	@RequestMapping("/jobCategory/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebCoreErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		JobCategory[] beans = manager.deleteByIds(ids);
		for (JobCategory bean : beans) {
			log.info("delete JobCategory id={}", bean.getId());
			cmsLogMng.operating(request, "tgJobCategory.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model, null);
	}


	private WebCoreErrors validateSave(JobCategory bean, Integer uploadFtpId,
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
		JobCategory entity = manager.findById(id);
		if (errors.ifNotExist(entity, JobCategory.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private JobCategoryMng manager;
}
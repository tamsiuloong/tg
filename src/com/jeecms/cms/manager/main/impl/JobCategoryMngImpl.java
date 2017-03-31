package com.jeecms.cms.manager.main.impl;

import com.jeecms.cms.dao.main.JobCategoryDao;
import com.jeecms.cms.entity.main.JobCategory;
import com.jeecms.cms.manager.main.JobCategoryMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobCategoryMngImpl implements JobCategoryMng {

	private JobCategoryDao dao;
	@Autowired
	public void setDao(JobCategoryDao dao) {
		this.dao = dao;
	}

	@Override
	public List<JobCategory> getList(String root) {
		Integer id = null;
		try{
			id = Integer.valueOf(root);
		}catch(Exception e){

		}
		return dao.getList(id);
	}

	@Override
	public JobCategory findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public JobCategory save(JobCategory bean, Integer pid) {
		if(pid!=null){
			JobCategory parent = new JobCategory();
			parent.setId(pid);
			bean.setParent(parent);
		}

		return dao.save(bean);
	}

	@Override
	public JobCategory update(JobCategory bean) {
		return dao.update(bean);
	}

	@Override
	public JobCategory[] deleteByIds(Integer[] ids) {
		JobCategory[] result = new JobCategory[ids.length];
		for(int i = 0 ;i < ids.length;i++){
			result[i]=dao.deleteById(ids[i]);

		}
		return result;
	}
}
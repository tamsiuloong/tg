package com.jeecms.cms.manager.main.impl;

import com.jeecms.cms.dao.main.IndustryCategoryDao;
import com.jeecms.cms.entity.main.IndustryCategory;
import com.jeecms.cms.manager.main.IndustryCategoryMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IndustryCategoryMngImpl implements IndustryCategoryMng {

	private IndustryCategoryDao dao;
	@Autowired
	public void setDao(IndustryCategoryDao dao) {
		this.dao = dao;
	}

	@Override
	public List<IndustryCategory> getList(String root) {
		Integer id = null;
		try{
			id = Integer.valueOf(root);
		}catch(Exception e){

		}
		return dao.getList(id);
	}

	@Override
	public IndustryCategory findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public IndustryCategory save(IndustryCategory bean, Integer pid) {
		if(pid!=null){
			IndustryCategory parent = new IndustryCategory();
			parent.setId(pid);
			bean.setParent(parent);
		}

		return dao.save(bean);
	}

	@Override
	public IndustryCategory update(IndustryCategory bean) {
		return dao.update(bean);
	}

	@Override
	public IndustryCategory[] deleteByIds(Integer[] ids) {
		IndustryCategory[] result = new IndustryCategory[ids.length];
		for(int i = 0 ;i < ids.length;i++){
			result[i]=dao.deleteById(ids[i]);

		}
		return result;
	}
}
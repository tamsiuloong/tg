package com.jeecms.cms.dao.main.impl;

import com.jeecms.cms.dao.main.IndustryCategoryDao;
import com.jeecms.cms.entity.main.IndustryCategory;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IndustryCategoryDaoImpl extends HibernateBaseDao<IndustryCategory, Integer>
		implements IndustryCategoryDao {
	@SuppressWarnings("unchecked")
	public List<IndustryCategory> getList(boolean containDisabled) {
		Finder f = Finder.create("from IndustryCategory bean");

		f.append(" order by bean.id asc");
		return find(f);
	}



	public IndustryCategory findById(Integer id) {
		IndustryCategory entity = get(id);
		return entity;
	}

	@Override
	public IndustryCategory update(IndustryCategory bean) {
		getSession().merge(bean);
		return bean;
	}


	public IndustryCategory save(IndustryCategory bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public List<IndustryCategory> getList(Integer pid) {
		Finder f = Finder.create("from IndustryCategory bean where 1 = 1 ");
		if(pid!=null&&!pid.equals(0)){
			f.append(" and bean.parent.id=:pid");
			f.setParam("pid",pid);
		}else{
			f.append(" and bean.parent is null");
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	public IndustryCategory deleteById(Integer id) {
		IndustryCategory entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<IndustryCategory> getEntityClass() {
		return IndustryCategory.class;
	}
}
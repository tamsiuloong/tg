package com.jeecms.cms.dao.main.impl;

import com.jeecms.cms.dao.main.JobCategoryDao;
import com.jeecms.cms.entity.main.JobCategory;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobCategoryDaoImpl extends HibernateBaseDao<JobCategory, Integer>
		implements JobCategoryDao {
	@SuppressWarnings("unchecked")
	public List<JobCategory> getList(boolean containDisabled) {
		Finder f = Finder.create("from JobCategory bean");

		f.append(" order by bean.id asc");
		return find(f);
	}



	public JobCategory findById(Integer id) {
		JobCategory entity = get(id);
		return entity;
	}

	@Override
	public JobCategory update(JobCategory bean) {
		getSession().merge(bean);
		return bean;
	}


	public JobCategory save(JobCategory bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public List<JobCategory> getList(Integer pid) {
		Finder f = Finder.create("from JobCategory bean where 1 = 1 ");
		if(pid!=null&&!pid.equals(0)){
			f.append(" and bean.parent.id=:pid");
			f.setParam("pid",pid);
		}else{
			f.append(" and bean.parent is null");
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	public JobCategory deleteById(Integer id) {
		JobCategory entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<JobCategory> getEntityClass() {
		return JobCategory.class;
	}
}
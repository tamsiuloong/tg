package com.jeecms.cms.manager.main;

import com.jeecms.cms.entity.main.JobCategory;

import java.util.List;

/**
 * Created by loong on 2017-03-29.
 */
public interface JobCategoryMng {

    List<JobCategory> getList(String root);

    JobCategory findById(Integer id);

    JobCategory save(JobCategory bean, Integer pid);

    JobCategory update(JobCategory bean);

    JobCategory[] deleteByIds(Integer[] ids);
}

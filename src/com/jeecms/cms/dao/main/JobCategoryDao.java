package com.jeecms.cms.dao.main;

import com.jeecms.cms.entity.main.JobCategory;

import java.util.List;

/**
 * Created by loong on 2017-03-29.
 */
public interface JobCategoryDao {
    JobCategory update(JobCategory bean);

//    JobCategory[] deleteByIds(Integer[] ids);

    JobCategory save(JobCategory bean);

    List<JobCategory> getList(Integer pid);

    JobCategory findById(Integer id);

    JobCategory deleteById(Integer id);
}

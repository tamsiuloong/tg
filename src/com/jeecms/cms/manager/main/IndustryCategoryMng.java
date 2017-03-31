package com.jeecms.cms.manager.main;

import com.jeecms.cms.entity.main.IndustryCategory;

import java.util.List;

/**
 * Created by loong on 2017-03-29.
 */
public interface IndustryCategoryMng {

    List<IndustryCategory> getList(String root);

    IndustryCategory findById(Integer id);

    IndustryCategory save(IndustryCategory bean, Integer pid);

    IndustryCategory update(IndustryCategory bean);

    IndustryCategory[] deleteByIds(Integer[] ids);
}

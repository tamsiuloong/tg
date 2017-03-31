package com.jeecms.cms.dao.main;

import com.jeecms.cms.entity.main.IndustryCategory;

import java.util.List;

/**
 * Created by loong on 2017-03-30.
 */
public interface IndustryCategoryDao {
    IndustryCategory update(IndustryCategory bean);

    IndustryCategory save(IndustryCategory bean);

    List<IndustryCategory> getList(Integer pid);

    IndustryCategory findById(Integer id);

    IndustryCategory deleteById(Integer id);
}

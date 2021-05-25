package cn.myst.web.service;

import cn.myst.web.pojo.Stu;

/**
 * @author ziming.xing
 * Create Date：2021/5/19
 */
public interface StuService {
    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);
}

package cn.myst.web.service.impl;

import cn.myst.web.mapper.StuMapper;
import cn.myst.web.pojo.Stu;
import cn.myst.web.service.StuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ziming.xing
 * Create Date：2021/5/19
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StuServiceImpl implements StuService {
    private final StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setName("Jack");
        stu.setAge(19);
        stuMapper.insert(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setId(id);
        stu.setName("Lucy");
        stu.setAge(20);
        stuMapper.updateById(stu);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {
        stuMapper.deleteById(id);
    }
}

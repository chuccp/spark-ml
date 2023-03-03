package com.kanke.ml.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kanke.ml.mapper.RecommendLogMapper;
import com.kanke.ml.model.RecommendLog;
import com.kanke.ml.service.RecommendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecommendLogServiceImpl implements RecommendLogService {

    @Autowired
    private RecommendLogMapper recommendLogMapper;

    @Override
    public void addRecommendLogS(String recommendType) {
        RecommendLog recommend = new RecommendLog();
        recommend.setRecommendStatus(1);
        recommend.setCreateTime(new Date());
        recommend.setRecommendType(recommendType);
        recommend.setUpdateTime(new Date());
        recommendLogMapper.insert(recommend);
    }

    @Override
    public RecommendLog findRecommendLogS(String recommendType) {
        QueryWrapper<RecommendLog> query = new QueryWrapper<>();
        query.eq("recommend_type", recommendType);
        return recommendLogMapper.selectOne(query);
    }
}

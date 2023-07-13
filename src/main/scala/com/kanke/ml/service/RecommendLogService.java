package com.kanke.ml.service;

import com.kanke.ml.entity.RecommendLog;

public interface RecommendLogService {

    void addRecommendLogS(String recommendType);

    RecommendLog findRecommendLogS(String recommendType);
}

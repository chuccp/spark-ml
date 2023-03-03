package com.kanke.ml.service;

import com.kanke.ml.model.RecommendLog;

import java.util.Date;

public interface RecommendLogService {

    void addRecommendLogS(String recommendType);

    RecommendLog findRecommendLogS(String recommendType);
}

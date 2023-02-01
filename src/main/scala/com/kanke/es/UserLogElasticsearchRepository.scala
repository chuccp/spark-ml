package com.kanke.es

import com.cokeframework.context.annotation.Component
import com.cokeframework.elasticsearch.ElasticsearchRepository
import com.kanke.model.UserLog

@Component
trait UserLogElasticsearchRepository extends ElasticsearchRepository[UserLog, String] {

}

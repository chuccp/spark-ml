package com.kanke.ml.controller

import com.kanke.ml.util.MethodUtil
import org.apache.commons.lang3.StringUtils
import org.apache.spark.internal.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RestController}
import org.springframework.web.context.WebApplicationContext

import javax.servlet.http.HttpServletRequest

@RestController
class RemoteController extends Logging{
  @Autowired
  var context: WebApplicationContext = _

  @RequestMapping(value = Array("remote/{taskname}"))
  def homes(@PathVariable(value = "taskname") taskname: String, arguments: String, request: HttpServletRequest): AnyRef = {
    this.log.info(s"远程执行：taskname：${taskname}  arguments:${arguments}")
    MethodUtil.execute(context, taskname,StringUtils.split(arguments,','))
    "OK"
  }
}

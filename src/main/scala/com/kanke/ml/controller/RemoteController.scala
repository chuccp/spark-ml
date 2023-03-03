package com.kanke.ml.controller

import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RestController}

import javax.servlet.http.HttpServletRequest

@RestController
class RemoteController {

  @RequestMapping(value = Array("remote/{taskname}"))
  def homes(@PathVariable(value = "taskname") taskname: String, request: HttpServletRequest): AnyRef = {
    null
  }
}

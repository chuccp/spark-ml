package com.kanke.ml

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

class ServletInitializer extends SpringBootServletInitializer{
  override protected def configure(application: SpringApplicationBuilder): SpringApplicationBuilder = application.sources(classOf[MlApplication])
}

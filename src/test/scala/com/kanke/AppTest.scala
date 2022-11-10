package com.kanke

import org.junit._
import Assert._

import java.io.{File, PrintWriter}
import java.util

@Test
class AppTest {

  @Test
  def testOK(): Unit = {


    var userIds = List.fill(6)(String.valueOf(Math.random() + 1).substring(2));


    var videoIds = List.fill(10)(String.valueOf(Math.random() + 1).substring(2));


    var userLogWriter = new PrintWriter(new File("user.log"))

    userIds.foreach((uid) => {
      var num = (Math.random() * 8).toInt
      var rVideoIds = List.fill(num)(videoIds.apply((Math.random() * videoIds.length).toInt)).distinct
      rVideoIds.foreach((vid) => {
        var line = uid+";"+vid+";"+(Math.random() * 10).toInt+"\n";
        userLogWriter.write(line)

      })

    })
    userLogWriter.flush()



    //        var userIds = ()=>{
    //            val userIdList:List[String]  = List();
    //            var a = 0
    //            for (a <- 1 to 10) {
    //                String.valueOf(Math.random() + 1).substring(2)
    //
    //            }
    //            return  userIdList
    //        }


  }
}



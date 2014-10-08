package com.devsummit

import akka.actor.Actor
import com.devsummit.Main.PingPong

/**
  * User: timreid
  * Date: 07/10/14
  */
class Pong extends Actor {
  override def receive: Receive = {
    case pingpong: PingPong => {
      println("Pong: " + pingpong.count)
      sender ! PingPong(pingpong.count + 1)
    }
  }
 }

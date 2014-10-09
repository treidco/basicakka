package com.devsummit

import akka.actor.Actor
import com.devsummit.Main.PingPong

class Pong extends Actor {
  override def receive: Receive = {
    case pingpong: PingPong => {
      println("Pong: " + pingpong.count)
      sender ! PingPong(pingpong.count + 1)
    }
  }
 }

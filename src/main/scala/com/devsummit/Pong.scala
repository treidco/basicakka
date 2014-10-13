package com.devsummit

import akka.actor.Actor
import com.devsummit.Main.PingPong

import scala.util.Random

class Pong extends Actor {
  override def receive: Receive = {
    case pingpong: PingPong => {
      println("Pong: " + pingpong.count)
      if (Random.nextBoolean()) throw new Exception else sender ! PingPong(pingpong.count + 1)
    }
  }
}

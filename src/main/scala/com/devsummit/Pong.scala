package com.devsummit

import akka.actor.Actor
import com.devsummit.Main.PingPong

import scala.util.Random

class RandomException(msg: String) extends Exception(msg)

class Pong extends Actor {
  override def receive: Receive = {
    case pingpong: PingPong => {
      println("Pong: " + pingpong.count)
      if (Random.nextBoolean()) throw new RandomException("Bang") else sender ! PingPong(pingpong.count + 1)
    }
  }
}

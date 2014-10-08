package com.devsummit

import akka.actor.{Actor, ActorRef, PoisonPill}
import com.devsummit.Main.{PingPong, Starter}

/**
 * User: timreid
 * Date: 07/10/14
 */
class Ping(p: ActorRef) extends Actor {
  override def receive: Receive = {
    case starter: Starter => p ! PingPong(starter.count)
    case pingpong: PingPong => {
      val count = pingpong.count
      println("Ping: " + count)
      if(count < 100) sender ! PingPong(pingpong.count + 1)
    }
  }
}

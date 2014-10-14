package com.devsummit

import akka.actor.{Actor, ActorRef}
import com.devsummit.Protocol._

class Ping extends Actor {

  val pong = context.actorSelection("../pong")

  override def receive: Receive = {
    case starter: Start => pong ! PingPong(starter.count)
    case pingpong: PingPong => {
      val count = pingpong.count
      println("Ping: " + count)
      if(count < 10) sender ! PingPong(pingpong.count + 1) else context.parent ! End
    }
  }
}

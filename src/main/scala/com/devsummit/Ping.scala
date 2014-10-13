package com.devsummit

import akka.actor.{Actor, ActorRef}
import com.devsummit.Main.{End, PingPong, Start}

class Ping(p: ActorRef) extends Actor {
  override def receive: Receive = {
    case starter: Start => p ! PingPong(starter.count)
    case pingpong: PingPong => {
      val count = pingpong.count
      println("Ping: " + count)
      if(count < 10) sender ! PingPong(pingpong.count + 1) else context.parent ! End
    }
  }
}

package com.devsummit

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.devsummit.Protocol.Start

class RandomException(msg: String) extends Exception(msg)

object Protocol {

  sealed trait Message

  case class Start(count: Int) extends Message

  case class PingPong(count: Int) extends Message

  case class PongState(count: Int, sender: ActorRef) extends Message

  case object RequestState extends Message

  case object End extends Message

}


object Main extends App {

  val system = ActorSystem("DevSummit")
  val supervisor = system.actorOf(Props[Supervisor], "supervisor")

  //  supervisor ! Start

  val local = system.actorOf(Props[LocalActor], "local")
  local ! Start

}

class LocalActor extends Actor {

  val remote = context.actorSelection("akka.tcp://RemoteAkka@127.0.0.1:2552/user/echo")

  override def receive: Receive = {
    case Start => remote ! "Hello World"
    case msg => println("Recieved echoed message: " + msg)

  }
}
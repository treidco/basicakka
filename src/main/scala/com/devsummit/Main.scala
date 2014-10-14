package com.devsummit

import akka.actor.{ActorRef, ActorSystem, Props}

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

  import com.devsummit.Protocol._

  val system = ActorSystem("DevSummit")
  val supervisor = system.actorOf(Props[Supervisor], "supervisor")

  supervisor ! Start

}
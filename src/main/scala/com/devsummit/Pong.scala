package com.devsummit

import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import com.devsummit.Main.{PingPong, PongState, RequestState}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Random

class RandomException(msg: String) extends Exception(msg)

class Pong extends Actor {

  var pongState: Option[PongState] = None
  var restarted = false

  override def receive: Receive = {
    case pingpong: PingPong => {
      println("Pong: " + pingpong.count)

      val currentState = PongState(pingpong.count, sender())
      pongState = Some(currentState)
      context.parent ! currentState

      if (!restarted && Random.nextBoolean())
        throw new RandomException("Bang")
      else
        sender ! PingPong(currentState.count + 1)
    }
  }

  override def postRestart(reason: Throwable) = {
    println("Restarting Pong Actor...")
    implicit val timeout = Timeout(5 seconds)

    restarted = true

    val future = context.parent ? RequestState
    val state = Await.result(future, timeout.duration).asInstanceOf[PongState]
    pongState = Some(state)

    println("Received state: " + state)
    state.sender ! PingPong(state.count + 1)
  }

}

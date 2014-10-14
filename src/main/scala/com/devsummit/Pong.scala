package com.devsummit

import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import com.devsummit.Protocol._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Random


class Pong extends Actor {

  var pongState: Option[PongState] = None
  var restarted = false

  override def receive: Receive = {
    case pingpong: PingPong => {
      println("Pong: " + pingpong.count)

      val currentState = PongState(pingpong.count, sender())
      persistState(currentState)

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

  private def persistState(state: PongState) {
    pongState = Some(state)
    context.parent ! state
  }

}

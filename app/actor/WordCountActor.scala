package actor

import akka.actor.Actor
import entities.Event
import service.EventService

/**
  * Created by rann.
  */
class WordCountActor(eventStateService: EventService) extends Actor {
  override def receive: Receive = {
    case Event(_, data, _) => data.split(" ").foreach(eventStateService.countWord)
  }
}

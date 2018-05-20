package actor

import akka.actor.Actor
import entities.Event
import service.EventService

/**
  * Created by rann.
  */
class EventTypeCountActor(eventStateService: EventService) extends Actor {
  override def receive: Receive = {
    case Event(eventType, _, _) => eventStateService.countEvent(eventType)
  }
}

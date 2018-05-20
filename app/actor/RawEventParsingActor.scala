package actor

import akka.actor.{Actor, ActorRef}
import entities.{Event, RawEvent}
import play.api.libs.json.Json.{fromJson, reads, writes}
import play.api.libs.json.{Format, JsSuccess, Json}

import scala.util.Try

/**
  * Created by rann.
  */
class RawEventParsingActor(eventTypeCountActor: ActorRef,
                           wordCountActor: ActorRef) extends Actor {

  private implicit val eventFormat: Format[Event] = Format[Event](reads[Event], writes[Event])

  override def receive: Receive = {
    case rawEvent: RawEvent =>
      Try(Json.parse(rawEvent.eventString)).map { jsValue =>
        fromJson[Event](jsValue) match {
          case JsSuccess(event, _) =>
            eventTypeCountActor ! event
            wordCountActor ! event
        }
      }
  }
}

package listener

import javax.inject.{Inject, Named, Singleton}

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import entities.RawEvent

import scala.sys.process._

/**
  * Created by rann.
  */
@Singleton
class EventsListener @Inject()(implicit actorSystem: ActorSystem,
                               @Named("rawEventParsingActor") rawEventParsingActor: ActorRef) {

  start()

  def start(): Unit = {
    Source("./generator-linux-amd64".lineStream_!)
      .runForeach(eventStr =>{
        rawEventParsingActor ! RawEvent(eventStr) })(ActorMaterializer())
  }
}
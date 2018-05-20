package actor

import javax.inject.Named

import akka.actor.{ActorRef, ActorSystem, Props}
import com.google.inject.{AbstractModule, Provides}
import listener.EventsListener
import service.EventService

/**
  * Created by rann.
  */
class ActorModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[EventsListener]).asEagerSingleton()
  }

  @Provides
  @Named("eventTypeCountActor")
  def getEventTypeCountActor(actorSystem: ActorSystem,
                             eventStateService: EventService): ActorRef = {
    actorSystem.actorOf(Props(new EventTypeCountActor(eventStateService)))
  }

  @Provides
  @Named("wordCountActor")
  def getWordCountActor(actorSystem: ActorSystem,
                        eventStateService: EventService): ActorRef = {
    actorSystem.actorOf(Props(new WordCountActor(eventStateService)))
  }

  @Provides
  @Named("rawEventParsingActor")
  def getRawEventParsingActor(actorSystem: ActorSystem,
                              @Named("eventTypeCountActor") eventTypeCountActor: ActorRef,
                              @Named("wordCountActor") wordCountActor: ActorRef): ActorRef = {
    actorSystem.actorOf(Props(new RawEventParsingActor(eventTypeCountActor, wordCountActor)))
  }

}

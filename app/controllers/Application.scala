package controllers

import javax.inject.{Inject, Singleton}

import entities.EventsStatus
import play.api._
import play.api.libs.json.Json.{reads, writes}
import play.api.libs.json.{Format, Json}
import play.api.mvc._
import service.EventService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Application @Inject()(controllerComponents: ControllerComponents,
                           eventStateService: EventService)
  extends AbstractController(controllerComponents){

  private implicit val eventsStatusFormat = Format[EventsStatus](reads[EventsStatus], writes[EventsStatus])

  def index = Action.async {
    eventStateService
      .fetchStats()
    .map(status => Ok(Json.toJson(status)))
  }

}
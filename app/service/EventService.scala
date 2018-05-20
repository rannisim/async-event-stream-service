package service

import java.util.concurrent.atomic.AtomicLong
import javax.inject.Singleton

import entities.{Event, EventsStatus}
import play.api.libs.json.Format
import play.api.libs.json.Json.{reads, writes}

import scala.collection.concurrent.TrieMap
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by rann.
  */
@Singleton
class EventService {

  private val counterByEventType = TrieMap[String, AtomicLong]()
  private val counterByWord = TrieMap[String, AtomicLong]()

  def countEvent(eventType: String): Unit = Future {
    counterByEventType
      .getOrElseUpdate(eventType, new AtomicLong())
      .incrementAndGet()
  }

  def countWord(data: String): Unit = Future {
    data
      .split(" ")
      .foreach { word =>
        counterByWord
          .getOrElseUpdate(word, new AtomicLong())
          .incrementAndGet()
      }
  }

  def fetchStats(): Future[EventsStatus] = Future {
    EventsStatus(
      counterByEventType.readOnlySnapshot().mapValues(_.get).toMap,
      counterByWord.readOnlySnapshot().mapValues(_.get).toMap)
  }
}

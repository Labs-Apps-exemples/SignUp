package models

import play.api.libs.json.Json

case class User(name: String, email: String)

object JsonFormats {


  implicit val userFormat = Json.format[User]

}
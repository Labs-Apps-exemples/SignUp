package models

case class User(name: String, email: String)

object JsonFormats {

	import play.api.libs.json.Json

	implicit val userFormat = Json.format[User]

}
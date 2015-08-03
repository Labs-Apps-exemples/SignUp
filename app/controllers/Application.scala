package controllers

import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{ Action, Controller }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

import scala.concurrent.Future

import play.modules.reactivemongo.MongoController
import reactivemongo.api.Cursor

import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._

object Application extends Controller with MongoController {

  import models._
  import models.JsonFormats._

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  val userForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> email
    )(User.apply)(User.unapply)
  )

  def index = Action {
    Ok(views.html.form(userForm))
  }

  def saveUser = Action{ implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.form(formWithErrors)),
      success => {
        collection.insert(success)
        Ok(views.html.success())
      }
    )
  }

  def findAll = Action.async {
    val cursor: Cursor[User] = collection.find(Json.obj("_id" -> Json.obj("$exists" -> true))).cursor[User]
    val futureUsersList: Future[List[User]] = cursor.collect[List]()
    futureUsersList.map { persons =>
      Ok(persons.toString())
    }
  }

}
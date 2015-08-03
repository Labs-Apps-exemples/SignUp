package controllers

import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{ Action, Controller }

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
        Ok(views.html.success())
      }
    )
  }

}
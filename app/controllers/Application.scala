package controllers

import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms._

import models.User

import play.api.mvc.{ Action, Controller }

object Application extends Controller {

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
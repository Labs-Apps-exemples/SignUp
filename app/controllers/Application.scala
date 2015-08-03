package controllers

import models.User

import play.api.mvc.{ Action, Controller }

object Application extends Controller {

  def index = Action {
    Ok("Hello")
  }

}
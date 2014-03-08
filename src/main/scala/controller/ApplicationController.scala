package controller

import skinny._
import skinny.filter._

/**
 * The base controller for this Skinny application.
 *
 * see also "http://skinny-framework.org/documentation/controller-and-routes.html"
 */
trait ApplicationController extends SkinnyController
  // with TxPerRequestFilter
  // with SkinnySessionFilter
  with ErrorPageFilter {

  val defaultDevice = "default"
  val smartDevice = "smartphone"

  private val extension = "ssp"
  private val mobileRegex = """.*(?i)(iphone|android|ipod).*""".r
  protected var device = defaultDevice

  def userAgent = request.getHeader("User-Agent")

  beforeAction() {
    device = userAgent match {
      case mobileRegex(m) => smartDevice
      case _ => defaultDevice
    }
  }

  override def render(path: String)(implicit format: Format = Format.HTML): String = {
    layout(device + "." + extension)
    super.render(path + "_" + device)(format)
  }
  // override def defaultLocale = Some(new java.util.Locale("ja"))

}


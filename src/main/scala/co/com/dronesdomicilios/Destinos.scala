package co.com.dronesdomicilios

class Destinos(var dests: List[Either[String, Coordenada]] = List()) {

  def agregar(c: Coordenada): Destinos = {
    val rc = Right(c)
    dests = rc::dests
    new Destinos(dests)
  }

  def agregar(error: String): Destinos = {
    val ls = Left(error)
    dests = ls::dests
    new Destinos(dests)
  }

  override def toString = {
    var text = ""
    dests.reverse.foreach(dst => {
      if(dst.isRight) text = text + dst.right.get.toString
      else text = text + dst.left.get.toString + "\n"
    })
    text
  }
}

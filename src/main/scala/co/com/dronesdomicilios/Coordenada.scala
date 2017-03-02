package co.com.dronesdomicilios

class Coordenada(var x: Int = 0, var y: Int = 0, var sentido: String = "Norte"){
  override def toString = s"($x, $y) dirección $sentido\n"
  def adelante: Coordenada = {
    sentido match {
      case "Norte" => new Coordenada(x, y+1, sentido)
      case "Sur" => new Coordenada(x, y-1, sentido)
      case "Oriente" => new Coordenada(x+1, y, sentido)
      case "Occidente" => new Coordenada(x-1, y, sentido)
    }
  }
  def izquierda: Coordenada = sentido match {
    case "Norte" => new Coordenada(x, y, "Occidente")
    case "Sur" => new Coordenada(x, y, "Oriente")
    case "Oriente" => new Coordenada(x, y, "Norte")
    case "Occidente" => new Coordenada(x, y, "Sur")
  }
  def derecha: Coordenada = sentido match {
    case "Norte" => new Coordenada(x, y, "Oriente")
    case "Oriente" => new Coordenada(x, y, "Sur")
    case "Sur" => new Coordenada(x, y, "Occidente")
    case "Occidente" => new Coordenada(x, y, "Norte")
  }
  def entreLimites(lim: Int): Boolean = 
    !(x > lim || x < -lim || y > lim || y < -lim)

}

object Coordenada {
  def origen: Coordenada = new Coordenada
}

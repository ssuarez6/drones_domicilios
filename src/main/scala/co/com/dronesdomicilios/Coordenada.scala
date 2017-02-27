package co.com.dronesdomicilios

class Coordenada(var x: Int = 0, var y: Int = 0, var sentido: String = "Norte"){
  override def toString = s"($x, $y) dirección $sentido\n"
  def A: Unit = {
    sentido match {
      case "Norte" => this.y = y+1
      case "Sur" => this.y = y-1
      case "Oriente" => this.x = x+1
      case "Occidente" => this.x = x-1
    }
  }
  def I: Unit = sentido match {
    case "Norte" => this.sentido = "Occidente"
    case "Sur" => this.sentido = "Oriente" //ask
    case "Oriente" => this.sentido = "Norte"
    case "Occidente" => this.sentido = "Sur"
  }
  def D: Unit = sentido match {
    case "Norte" => this.sentido = "Oriente"
    case "Oriente" => this.sentido = "Sur"
    case "Sur" => this.sentido = "Occidente"
    case "Occidente" => this.sentido = "Norte"
  }
  def esValida: Boolean = !(x > 10 || x < -10 || y > 10 || y < -10)
}

package co.com.dronesdomicilios

trait Comando 

object Adelante extends Comando
object Derecha extends Comando
object Izquierda extends Comando

class Ruta(val text: List[Char]) {
  override def toString: String = {
    var text = ""
    this.text.foreach(c => text = text + c)
    text
  }
}

case class PackRutas(val rts: List[Ruta])

class VerificadorRuta(){
  def validaDesde(rt: Ruta, coor: Coordenada, lim: Int): Boolean = {
    var copy = new Coordenada(coor.x, coor.y, coor.sentido)
    var valida = true
    rt.text.foreach(acc => {
      acc match {
        case 'A' => copy = copy.adelante
        case 'I' => copy = copy.izquierda
        case 'D' => copy = copy.derecha
      }
      valida = copy.entreLimites(lim)
    })
    valida
  }  

  def esValida(rt: Ruta): Boolean = 
    rt.text.filter(x => x!='A' && x!='I' && x!='D').size == 0
}

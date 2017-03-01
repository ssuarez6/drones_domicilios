package co.com.dronesdomicilios

sealed trait RutaFactory

object RutaFactory {
  case class RutaInvalida(val ruta: List[Char]) extends RutaFactory

  class RutaValida(val text: List[Char]) extends RutaFactory {
    def validaDesde(coor: Coordenada): Boolean = {
      var copy = new Coordenada(coor.x, coor.y, coor.sentido)
      var valida = true
      text.foreach(acc => {
        acc match {
          case 'A' => copy = copy.adelante
          case 'I' => copy = copy.izquierda
          case 'D' => copy = copy.derecha
        }
        if(!copy.esValida) valida = false
      })
      valida
    }  

    override def toString: String = {
      var text = ""
      this.text.foreach(c => text = text + c)
      text
    }
  }


  def apply(text: List[Char]): RutaFactory = {
    val test = text.filter(x => x!='A' && x!='I' && x!='D')
    if(test.size > 0) new RutaInvalida(text)
    else new RutaValida(text)
  }
}

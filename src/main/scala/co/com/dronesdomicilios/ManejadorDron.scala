package co.com.dronesdomicilios

import scala.io.Source
import java.io._

class ManejadorDron(val limiteEntregas: Int = 3){
  import RutaFactory._
  val impresora = new PrintWriter(new File("out.txt"))
  val dron = new Dron

  def verificarValidez(listaRutas: List[RutaFactory]): (Boolean, List[RutaValida]) = {
    val todasValidas = listaRutas.forall({
      case _: RutaInvalida => false
      case _ => true
    })
    if(todasValidas) (todasValidas, listaRutas.map(x => x.asInstanceOf[RutaValida]))
    else (false, Nil)
  }

  def crearPackRutas: Either[Boolean, List[PackRutas]] = {
    val lineasEntrada: List[List[Char]] = Source.fromFile("in.txt").getLines.toList.map(_.toList)
    val listaRutas: List[RutaFactory] = lineasEntrada.map(rt => RutaFactory(rt))
    val validez = verificarValidez(listaRutas)
    val todasValidas = validez._1
    if(todasValidas){
      val packs = crearPacks(validez._2)
      println(packs)
      Right(packs)
    }else{
      Left(true)
    }
  }

  def crearPacks(rutas: List[RutaValida]): List[PackRutas] = {
    def crearPacks(rutas: List[RutaValida], salida: List[PackRutas]): List[PackRutas] = {
      if(rutas.isEmpty) salida
      else crearPacks(rutas drop limiteEntregas, salida :+ PackRutas(rutas take limiteEntregas))
    }
    crearPacks(rutas, List[PackRutas]() )
  }

  def despachar: Either[String, String]= {
    val creacion = crearPackRutas
    if(creacion.isRight){
      val packs = creacion.right.get
      println("Paquetes:")
      println(packs)
      packs.foreach(pck => dron.recibirPack(pck))
      Right(dron.reporte)
    }else{
      val existeFichero = creacion.left.get
      if(existeFichero) Left("Existe al menos una ruta con caracteres invalidos")
      else Left("No se pudo abrir el fichero de entrada, verifique que exista")
    }
  }

  def escribirEnFichero = {
    val resultado = despachar
    if(resultado.isRight){
      impresora.write("== Reporte de entregas ==\n")
      impresora.write(resultado.right.get)
    }
    else impresora.write(resultado.left.get)
    impresora.close
  }
}

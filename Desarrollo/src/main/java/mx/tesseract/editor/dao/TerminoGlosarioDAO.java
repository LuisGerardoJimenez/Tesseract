package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.bs.ReferenciaEnum.Clave;
import mx.tesseract.bs.ReferenciaEnum.TipoReferencia;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.util.Constantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("glosarioDAO")
public class TerminoGlosarioDAO extends ElementoDAO {

//	public List<TerminoGlosario>consultarTerminosGlosario(Integer idProyecto) {
//		List<TerminoGlosario> lista = new ArrayList<TerminoGlosario>();
//		System.out.println("GlosarioDAO");
//		List<TerminoGlosario> elementos = findAllByIdProyectoAndClave (TerminoGlosario.class, Clave.GLS, idProyecto);
//		if (!elementos.isEmpty())
//			for (Elemento elemento : elementos) {
//				System.out.println(elemento.getNombre());
//				lista.add((TerminoGlosario) elemento);
//			}
//		return lista;
//	}
//	
//	public TerminoGlosario findTerminoGlosarioByNombre(String nombre, Integer idProyecto) {
//		TerminoGlosario terminoGlosario = (TerminoGlosario) findByNombre(TipoReferencia.TERMINOGLS, nombre, idProyecto);
//		return terminoGlosario;
//	}
//	
//	public TerminoGlosario findTerminoGlosarioByNombreAndId(Integer id, String nombre, Integer idProyecto) {
//		TerminoGlosario terminoGlosario = (TerminoGlosario) findByNombreAndId(TipoReferencia.TERMINOGLS, id, nombre, idProyecto);
//		return terminoGlosario;
//	}
//	
//	public String siguienteNumeroTerminoGlosario(Integer idProyecto) {
//		return siguienteNumero(TipoReferencia.TERMINOGLS, idProyecto);
//	}
	
}

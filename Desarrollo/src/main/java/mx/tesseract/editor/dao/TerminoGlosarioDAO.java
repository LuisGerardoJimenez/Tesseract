package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.bs.ReferenciaEnum.TipoReferencia;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.util.Constantes;

import org.springframework.stereotype.Repository;

@Repository("glosarioDAO")
public class TerminoGlosarioDAO extends ElementoDAO {

	public List<TerminoGlosario>consultarTerminosGlosario(Integer idProyecto) {
		List<TerminoGlosario> lista = new ArrayList<TerminoGlosario>();
		List<Elemento> elementos = consultarElementos(TipoReferencia.TERMINOGLS, idProyecto);
		if (!elementos.isEmpty())
			for (Elemento elemento : elementos) {
				lista.add((TerminoGlosario) elemento);
			}
		return lista;
	}
	
	public TerminoGlosario findTerminoGlosarioByNombre(String nombre, Integer idProyecto) {
		TerminoGlosario terminoGlosario = (TerminoGlosario) findByNombre(TipoReferencia.TERMINOGLS, nombre, idProyecto);
		return terminoGlosario;
	}
	
	public TerminoGlosario findTerminoGlosarioByNombreAndId(Integer id, String nombre, Integer idProyecto) {
		TerminoGlosario terminoGlosario = (TerminoGlosario) findByNombreAndId(TipoReferencia.TERMINOGLS, id, nombre, idProyecto);
		return terminoGlosario;
	}
	
	public String siguienteNumeroTerminoGlosario(Integer idProyecto) {
		return siguienteNumero(TipoReferencia.TERMINOGLS, idProyecto);
	}
	
}

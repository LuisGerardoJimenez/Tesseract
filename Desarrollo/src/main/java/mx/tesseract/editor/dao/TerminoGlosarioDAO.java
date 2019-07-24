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

	public List<TerminoGlosario>consultarTerminosGlosario(int idProyecto) {
		System.out.println("ya estamos en el daodeglosario");
		List<TerminoGlosario> lista = new ArrayList<TerminoGlosario>();
		List<Elemento> elementos = super.consultarElementos(TipoReferencia.TERMINOGLS, idProyecto);
		if (elementos != null)
			for (Elemento elemento : elementos) {
				lista.add((TerminoGlosario) elemento);
			}
		return lista;
	}

	/* public String siguienteNumeroTerminoGlosario(int idProyecto) { return
	 * super.siguienteNumero(TipoReferencia.TERMINOGLS, idProyecto); }
	 */
}

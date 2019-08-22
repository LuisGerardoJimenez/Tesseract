package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.enums.ReferenciaEnum.Clave;

@Service("reglaNegocioBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ReglaNegocioBs {
	
	@Autowired
	private ElementoDAO elementoDAO;

	public List<ReglaNegocio> consultarGlosarioProyecto(Integer idProyecto) {
		List<ReglaNegocio> listReglaNegocio = elementoDAO.findAllByIdProyectoAndClave(ReglaNegocio.class, idProyecto, Clave.RN);
		return listReglaNegocio;
	}

}

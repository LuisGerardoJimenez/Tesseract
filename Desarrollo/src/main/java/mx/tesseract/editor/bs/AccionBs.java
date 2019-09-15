package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.editor.dao.AccionDAO;
import mx.tesseract.editor.entidad.Accion;

@Service("accionBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AccionBs {

	@Autowired
	private AccionDAO accionDAO;
	
	public List<Accion> consultarAccionesByPantalla(Integer idPantalla) {
		List<Accion> acciones = accionDAO.findAllByPantalla(idPantalla);
		return acciones;
	}
}

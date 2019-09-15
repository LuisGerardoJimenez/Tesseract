package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.TipoAccion;
import mx.tesseract.util.TESSERACTException;

@Service("tipoAccionBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TipoAccionBs {

	@Autowired
	private GenericoDAO genericoDAO;
	
	public List<TipoAccion> consultarTiposAccion() {
		List<TipoAccion> tiposAccion = genericoDAO.findAll(TipoAccion.class);
		if (tiposAccion == null) {
			throw new TESSERACTException("No se pueden consultar los tipos de acciones.", "MSG12");			
		}
		return tiposAccion;
	}
	
}

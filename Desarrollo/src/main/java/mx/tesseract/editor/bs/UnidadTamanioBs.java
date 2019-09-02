package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.UnidadTamanio;
import mx.tesseract.util.TESSERACTException;

@Service("unidadTamanioBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class UnidadTamanioBs {
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	public List<UnidadTamanio> consultarUnidadesTamanio() {
		List<UnidadTamanio> listUnidadTamanio = genericoDAO.findAll(UnidadTamanio.class);
		if (listUnidadTamanio.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar las unidades.","MSG12");
		}
		return listUnidadTamanio;
	}

}

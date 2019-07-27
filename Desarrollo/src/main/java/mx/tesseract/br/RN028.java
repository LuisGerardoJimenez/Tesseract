package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.entidad.Modulo;

@Service("rN028")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN028 {

	@Autowired
	private ModuloDAO moduloDAO;
	
	public Boolean isValidRN028(Modulo entidad) {
		Boolean valido = true;
		Modulo modulo = moduloDAO.hasReferenciaElementos(entidad.getId());
		if (modulo != null) {
			valido = false;
		}
		return valido;
	}
	
}

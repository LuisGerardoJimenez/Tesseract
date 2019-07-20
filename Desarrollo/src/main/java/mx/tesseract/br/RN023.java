package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.entidad.Modulo;

@Service("rN023")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN023 {
	
	@Autowired
	private ModuloDAO moduloDAO;
	
	public Boolean isValidRN023(Modulo entidad) {
		Boolean valido = true;
		Modulo modulo = moduloDAO.findModuloByClave(entidad.getClave());
		if (modulo != null) {
			valido = false;
		}
		return valido;
	}
	
}

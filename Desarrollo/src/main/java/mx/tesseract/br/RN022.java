package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Proyecto;

@Service("rN022")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN022 {
	
	@Autowired
	private ProyectoDAO proyectoDAO;
	
	public Boolean isValidRN022(Proyecto entidad) {
		Boolean valido = true;
		Proyecto proyecto = proyectoDAO.findByClave(entidad.getClave());
		if (proyecto != null) {
			valido = false;
		}
		return valido;
	}

}

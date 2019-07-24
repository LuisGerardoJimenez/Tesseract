package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;

@Service("rN027")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN027 {
	
	@Autowired
	private ColaboradorDAO colaboradorDAO;
	
	public Boolean isValidRN027(Colaborador entidad) {
		Boolean valido = true;
		Colaborador proyecto = colaboradorDAO.hasProyectos(entidad);
		if (proyecto != null) {
			valido = false;
		}
		return valido;
	}

}

package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;

@Service("RN036")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN036 {
	
	@Autowired
	private ColaboradorDAO colaboradorDAO;
	
	public Boolean isValidRN036(Colaborador entidad) {
		Boolean valido = true;
		Colaborador colaborador = colaboradorDAO.findColaboradorByCorreo(entidad.getCorreoElectronico());
		if (colaborador != null) {
			valido = false;
		}
		return valido;
	}

}
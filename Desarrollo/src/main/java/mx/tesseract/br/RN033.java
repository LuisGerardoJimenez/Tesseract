package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;

@Service("rN033")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN033 {

	@Autowired
	private ColaboradorDAO colaboradorDAO;

	public Boolean isValidRN033(Colaborador entidad) {
		Boolean valido = true;
		Colaborador colaborador = colaboradorDAO.findColaboradorByCURP(entidad.getCurp());
		if (colaborador != null) {
			valido = false;
		}
		return valido;
	}

}

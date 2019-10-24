package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.Operador;
import mx.tesseract.util.TESSERACTException;

@Service("operadorBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)

public class OperadorBs {
	
	@Autowired
	private GenericoDAO genericoDAO;

	public List<Operador> consultarOperador() {
		List<Operador> listOperadores = genericoDAO.findAll(Operador.class);
		if (listOperadores.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar los operadores.", "MSG29");
		}
		return listOperadores;
	}

}

package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.TipoDato;
import mx.tesseract.util.TESSERACTException;

@Service("tipoDatoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TipoDatoBs {
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	public List<TipoDato> consultarTiposDato() {
		List<TipoDato> listTiposDato = genericoDAO.findAll(TipoDato.class);
		if (listTiposDato.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar los tipos de dato.", "MSG29");
		}
		return listTiposDato;
	}

}

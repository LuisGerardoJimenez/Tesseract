package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.editor.dao.AtributoDAO;
import mx.tesseract.editor.dao.ElementoDAO;

@Service("atributoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AtributoBs {
	
	@Autowired
	private AtributoDAO atributoDAO;
	
	
	public List<Atributo> consultarAtributosByEntidad(Integer idEntidad) {
		List<Atributo> atributos = atributoDAO.findByIdEntidad(idEntidad);
		return atributos;
	}
	

}

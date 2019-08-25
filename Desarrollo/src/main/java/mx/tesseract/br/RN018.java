package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.enums.ReferenciaEnum.Clave;

@Service("rN018")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN018 {	
	
	@Autowired
	private ElementoDAO elementoDAO;
	
	public Boolean isValidRN018(TerminoGlosarioDTO entidad) {
		Boolean valido = true;
		TerminoGlosario terminoGlosario = null;
		if (entidad.getId() == null) {
			terminoGlosario = elementoDAO.findElementoHasCasoUsoAsociado(entidad.getIdProyecto(),entidad.getId(), entidad.getNombre(), Clave.GLS);
		}
		if (terminoGlosario != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN018(MensajeDTO entidad) {
		Boolean valido = true;
		Mensaje mensaje = null;
		if (entidad.getId() == null) {
			mensaje = elementoDAO.findElementoHasCasoUsoAsociado(entidad.getIdProyecto(),entidad.getId(), entidad.getNombre(), Clave.GLS);
		}
		if (mensaje != null) {
			valido = false;
		}
		return valido;
	}

}

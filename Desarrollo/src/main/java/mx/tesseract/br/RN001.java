package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.editor.dao.PantallaDAO;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.enums.ReferenciaEnum.Clave;

@Service("rN001")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN001 {
	
	@Autowired
	private PantallaDAO pantallaDAO;
	
	public Boolean isValidRN001(PantallaDTO entidad) {
		Boolean valido = true;
		Pantalla pantalla;
		if (entidad.getId() == null) {
			pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndNumero(entidad.getIdProyecto(), Clave.IU, entidad.getIdModulo(), entidad.getNumero());
		} else {
			pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndIdAndNumero(entidad.getIdProyecto(), Clave.IU, entidad.getIdModulo(), entidad.getId(), entidad.getNumero());
		}
		if (pantalla != null) {
			valido = false;
		}
		return valido;
	}

}

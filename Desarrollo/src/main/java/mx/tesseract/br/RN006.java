package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.dao.TerminoGlosarioDAO;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.TerminoGlosario;

@Service("rN006")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN006 {

	@Autowired
	private ProyectoDAO proyectoDAO;
	
	@Autowired
	private ModuloDAO moduloDAO;
	
	@Autowired
	private TerminoGlosarioDAO terminoGlosarioDAO;
	
	public Boolean isValidRN006(Proyecto entidad) {
		Boolean valido = true;
		Proyecto proyecto;
		if (entidad.getId() == null) {
			proyecto = proyectoDAO.findByNombre(entidad.getNombre());
		} else {
			proyecto = proyectoDAO.findByNombreAndId(entidad.getNombre(), entidad.getId());
		}
		if (proyecto != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(Modulo entidad) {
		Boolean valido = true;
		Modulo modulo;
		if (entidad.getId() == null) {
			modulo = moduloDAO.findModuloByName(entidad.getNombre());
		} else {
			modulo = moduloDAO.findModuloByNombreAndId(entidad.getNombre(), entidad.getId());
		}
		if (modulo != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(TerminoGlosario entidad) {
		Boolean valido = true;
		TerminoGlosario terminoGlosario;
		if (entidad.getId() == null) {
			terminoGlosario = terminoGlosarioDAO.findTerminoGlosarioByNombre(entidad.getNombre(), entidad.getProyecto().getId());
		} else {
			terminoGlosario = terminoGlosarioDAO.findTerminoGlosarioByNombreAndId(entidad.getId(), entidad.getNombre(), entidad.getId());
		}
		if (terminoGlosario != null) {
			valido = false;
		}
		return valido;
	}
	
}

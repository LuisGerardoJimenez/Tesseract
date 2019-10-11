package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.dto.AccionDTO;
import mx.tesseract.dto.ActorDTO;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.dto.AtributoDTO;
import mx.tesseract.dto.EntidadDTO;
import mx.tesseract.dto.ReglaNegocioDTO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.AccionDAO;
import mx.tesseract.editor.dao.AtributoDAO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.dao.PantallaDAO;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.TerminoGlosario;

@Service("rN006")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN006 {

	@Autowired
	private ProyectoDAO proyectoDAO;
	
	@Autowired
	private ModuloDAO moduloDAO;
	
	@Autowired
	private ElementoDAO elementoDAO;
	
	@Autowired
	private AtributoDAO atributoDAO;
	
	@Autowired
	private PantallaDAO pantallaDAO;
	
	@Autowired
	private AccionDAO accionDAO;
	
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
	
	public Boolean isValidRN006(Modulo entidad, Integer idProyecto) {
		Boolean valido = true;
		Modulo modulo;
		if (entidad.getId() == null) {
			modulo = moduloDAO.findModuloByNameAndProyecto(entidad.getNombre(), idProyecto);
		} else {
			modulo = moduloDAO.findModuloByNombreAndIdAndProyecto(entidad.getNombre(), entidad.getId(), idProyecto);
		}
		if (modulo != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(CasoUso entidad) {
		Boolean valido = true;
		CasoUso casoUso;
		if (entidad.getId() == null) {
			casoUso = elementoDAO.findAllByIdProyectoAndNombreAndClave(CasoUso.class, entidad.getProyecto().getId(), entidad.getNombre(), Clave.CU); 
		} else {
			casoUso = elementoDAO.findAllByIdProyectoAndIdAndNombreAndClave(CasoUso.class,  entidad.getProyecto().getId(), entidad.getId(), entidad.getNombre(), Clave.CU);
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(TerminoGlosarioDTO entidad) {
		Boolean valido = true;
		TerminoGlosario terminoGlosario;
		if (entidad.getId() == null) {
			terminoGlosario = elementoDAO.findAllByIdProyectoAndNombreAndClave(TerminoGlosario.class, entidad.getIdProyecto(), entidad.getNombre(), Clave.GLS);
		} else {
			terminoGlosario = elementoDAO.findAllByIdProyectoAndIdAndNombreAndClave(TerminoGlosario.class, entidad.getIdProyecto(), entidad.getId(), entidad.getNombre(), Clave.GLS);
		}
		if (terminoGlosario != null) {
			valido = false;
		}
		return valido;
	}
	
	
	public Boolean isValidRN006(ActorDTO entidad) {
		Boolean valido = true;
		Actor actor;
		if (entidad.getId() == null) {
			actor = elementoDAO.findAllByIdProyectoAndNombreAndClave(Actor.class, entidad.getIdProyecto(), entidad.getNombre(), Clave.ACT);
		} else {
			actor = elementoDAO.findAllByIdProyectoAndIdAndNombreAndClave(Actor.class, entidad.getIdProyecto(), entidad.getId(), entidad.getNombre(), Clave.ACT);
		}
		if (actor != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(MensajeDTO entidad) {
		Boolean valido = true;
		Mensaje mensaje;
		mensaje = elementoDAO.findAllByIdProyectoAndNombreAndClave(Mensaje.class, entidad.getIdProyecto(), entidad.getNombre(), Clave.MSG);
		if (mensaje != null) {
		valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(EntidadDTO entidad) {
		Boolean valido = true;
		Entidad entidadDB;
		if (entidad.getId() == null) {
			entidadDB = elementoDAO.findAllByIdProyectoAndNombreAndClave(Entidad.class, entidad.getIdProyecto(), entidad.getNombre(), Clave.ENT);
		} else {
			entidadDB = elementoDAO.findAllByIdProyectoAndIdAndNombreAndClave(Entidad.class, entidad.getIdProyecto(), entidad.getId(), entidad.getNombre(), Clave.ENT);
		}
		if (entidadDB != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(AtributoDTO entidad) {
		Boolean valido = true;
		Atributo atributo;
		if (entidad.getId() == null) {
			atributo = atributoDAO.findAtributoByNombreAndEntidad(entidad.getNombre(), entidad.getIdEntidad());
		} else {
			atributo = atributoDAO.findAtributoByNombreAndIdAndEntidad(entidad.getNombre(), entidad.getId(), entidad.getIdEntidad());
		}
		if (atributo != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(ReglaNegocioDTO entidad) {
		Boolean valido = true;
		ReglaNegocio reglaNegocio;
		if (entidad.getId() == null) {
			reglaNegocio = elementoDAO.findAllByIdProyectoAndNombreAndClave(ReglaNegocio.class, entidad.getIdProyecto(), entidad.getNombre(), Clave.RN);
		} else {
			reglaNegocio = elementoDAO.findAllByIdProyectoAndIdAndNombreAndClave(ReglaNegocio.class, entidad.getIdProyecto(), entidad.getId(), entidad.getNombre(), Clave.RN);
		}
		if (reglaNegocio != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(PantallaDTO entidad) {
		Boolean valido = true;
		Pantalla pantalla;
		if (entidad.getId() == null) {
			pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndNombre(entidad.getIdProyecto(), Clave.IU, entidad.getIdModulo(), entidad.getNombre());
		} else {
			pantalla = pantallaDAO.findByIdProyectoAndIdModuloAndIdAndNombre(entidad.getIdProyecto(), Clave.IU, entidad.getIdModulo(), entidad.getId(), entidad.getNombre());
		}
		if (pantalla != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN006(AccionDTO entidad) {
		Boolean valido = true;
		Accion accion;
		if (entidad.getId() == null) {
			accion = accionDAO.findByNombreAndIdPantalla(entidad.getNombre(), entidad.getIdPantalla());
		} else {
			accion = accionDAO.findByNombreAndIdAndIdPantalla(entidad.getNombre(), entidad.getId(), entidad.getIdPantalla());
		}
		if (accion != null) {
			valido = false;
		}
		return valido;
	}
	
}

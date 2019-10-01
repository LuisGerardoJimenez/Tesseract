package mx.tesseract.editor.bs;

import java.util.List;

import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN023;
import mx.tesseract.br.RN028;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.util.TESSERACTException;
//import mx.tesseract.editor.dao.CasoUsoActorDAO;
//import mx.tesseract.editor.model.Pantalla;
//import mx.tesseract.editor.model.Paso;
//import mx.tesseract.editor.model.PostPrecondicion;
//import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.util.TESSERACTValidacionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("moduloBS")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ModuloBs {
	private Proyecto proyecto;

	@Autowired
	private RN006 rn006;

	@Autowired
	private RN023 rn023;

	@Autowired
	private RN028 rn028;
	@Autowired
	private ModuloDAO moduloDAO;

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ProyectoBs proyectoBs;

	public List<Modulo> consultarModulosProyecto(Integer idProyecto) {
		List<Modulo> modulos = moduloDAO.findByIdProyecto(idProyecto);
		return modulos;
	}

	public Modulo consultarModuloById(Integer id) {
		Modulo modulo = genericoDAO.findById(Modulo.class, id);
		if (modulo == null) {
			throw new TESSERACTException("No se puede consultar el proyecto.", "MSG12");
		}
		return modulo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarModulo(Modulo model, Integer idProyecto) {
		if (rn023.isValidRN023(model)) {
			if (rn006.isValidRN006(model)) {
				proyecto = proyectoBs.consultarProyecto(idProyecto);
				model.setProyecto(proyecto);
				genericoDAO.save(model);
			} else {
				throw new TESSERACTValidacionException("EL nombre del módulo ya existe.", "MSG7",
						new String[] { "El", "Módulo", model.getNombre() }, "model.nombre");
			}
		} else {
			throw new TESSERACTValidacionException("La clave del módulo ya existe.", "MSG7",
					new String[] { "La", "clave", model.getClave() }, "model.clave");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarModulo(Modulo model, Integer idProyecto) {
		if (rn006.isValidRN006(model)) {
			proyecto = proyectoBs.consultarProyecto(idProyecto);
			model.setProyecto(proyecto);
			genericoDAO.update(model);
		} else {
			throw new TESSERACTValidacionException("EL nombre del módulo ya existe.", "MSG7",
					new String[] { "El", "Módulo", model.getNombre() }, "model.nombre");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void eliminarModulo(Modulo model) {
		if (rn028.isValidRN028(model)) {
			genericoDAO.delete(model);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

}

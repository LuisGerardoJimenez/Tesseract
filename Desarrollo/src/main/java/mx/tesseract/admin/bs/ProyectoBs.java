package mx.tesseract.admin.bs;

import java.util.ArrayList;
import java.util.List;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.admin.entidad.Rol;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN022;
import mx.tesseract.br.RN034;
import mx.tesseract.br.RN035;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("proyectoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ProyectoBs {

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ProyectoDAO proyectoDAO;

	@Autowired
	private ColaboradorDAO colaboradorDAO;

	@Autowired
	private RN022 rn022;

	@Autowired
	private RN006 rn006;

	@Autowired
	private RN035 rn035;

	@Autowired
	private RN034 rn034;

	public List<Proyecto> consultarProyectos() {
		List<Proyecto> proyectos = genericoDAO.findAll(Proyecto.class);
		return proyectos;
	}

	public List<Proyecto> consultarProyectosByColaborador(String curp) {
		List<Proyecto> proyectos = proyectoDAO.findByCURPColaborador(curp);
		return proyectos;
	}

	public Proyecto consultarProyecto(Integer idProyecto) {
		Proyecto proyecto = genericoDAO.findById(Proyecto.class, idProyecto);
		if (proyecto == null) {
			throw new TESSERACTException("No se puede consultar el proyecto.", "MSG12");
		}
		return proyecto;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarProyecto(Proyecto model) {
		if (rn022.isValidRN022(model)) {
			if (rn006.isValidRN006(model)) {
				if (rn035.isValidRN035(model.getFechaInicioProgramada(), model.getFechaTerminoProgramada())) {
					agregarLiderProyecto(model);
					genericoDAO.save(model);
					genericoDAO.saveList(model.getProyecto_colaboradores());
				} else {
					throw new TESSERACTValidacionException("El usuario ingresó en desorden las fechas.", "MSG22",
							new String[] { "fecha de término programada", "fecha de inicio programada" },
							"model.fechaTerminoProgramada");
				}
			} else {
				throw new TESSERACTValidacionException("El nombre del proyecto ya existe.", "MSG7",
						new String[] { "El", "Proyecto", model.getNombre() }, "model.nombre");
			}
		} else {
			throw new TESSERACTValidacionException("La clave del proyecto ya existe.", "MSG7",
					new String[] { "El", "Proyecto", model.getClave() }, "model.clave");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarProyecto(Proyecto model) {
		if (rn022.isValidRN022(model)) {
			if (rn006.isValidRN006(model)) {
				if (rn035.isValidRN035(model.getFechaInicioProgramada(), model.getFechaTerminoProgramada())) {
					genericoDAO.update(model);
					editarLiderProyecto(model);
				} else {
					throw new TESSERACTValidacionException("El usuario ingresó en desorden las fechas.", "MSG22",
							new String[] { "fecha de término programada", "fecha de inicio programada" },
							"model.fechaTerminoProgramada");
				}
			} else {
				throw new TESSERACTValidacionException("El nombre del proyecto ya existe.", "MSG7",
						new String[] { "El", "Proyecto", model.getNombre() }, "model.nombre");
			}
		} else {
			throw new TESSERACTValidacionException("La clave del proyecto ya existe.", "MSG7",
					new String[] { "El", "Proyecto", model.getClave() }, "model.clave");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarProyecto(Proyecto model) {
		if (rn034.isValidRN034(model)) {
			genericoDAO.delete(model);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

	private void agregarLiderProyecto(Proyecto model) {
		try {
			Colaborador seleccionado = colaboradorDAO.findColaboradorByCURP(model.getColaboradorCurp());
			Rol rol = genericoDAO.findById(Rol.class, Constantes.ROL_LIDER);
			ColaboradorProyecto colaboradorproyecto = new ColaboradorProyecto(seleccionado, rol, model);
			model.getProyecto_colaboradores().add(colaboradorproyecto);
		} catch (Exception e) {
			throw new TESSERACTException("No se puede agregar lider de proyecto.", "MSG12");
		}
	}

	private void editarLiderProyecto(Proyecto model) {
		try {
			ColaboradorProyecto lider = null;
			ColaboradorProyecto colaboradorproyecto = null;
			Colaborador seleccionado = colaboradorDAO.findColaboradorByCURP(model.getColaboradorCurp());
			Rol rol = genericoDAO.findById(Rol.class, Constantes.ROL_LIDER);
			for (ColaboradorProyecto colaborador : model.getProyecto_colaboradores()) {
				if (colaborador.getRol().getId() == Constantes.ROL_LIDER) {
					lider = colaborador;
				}
				if (seleccionado.getCurp().equals(colaborador.getColaborador().getCurp())) {
					colaboradorproyecto = colaborador;
				}
			}
			if (colaboradorproyecto == null) {
				colaboradorproyecto = new ColaboradorProyecto(seleccionado, rol, model);
				model.getProyecto_colaboradores().add(colaboradorproyecto);
				model.getProyecto_colaboradores().remove(lider);
				genericoDAO.update(colaboradorproyecto);
				genericoDAO.delete(lider);
			} else {
				if (lider.getId() != colaboradorproyecto.getId()) {
					colaboradorproyecto.setRol(rol);
					model.getProyecto_colaboradores().remove(lider);
					genericoDAO.update(colaboradorproyecto);
					genericoDAO.delete(lider);
				}
			}
		} catch (Exception e) {
			throw new TESSERACTException("No se puede editar lider de proyecto.", "MSG12");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modificarColaboradoresProyecto(Proyecto model, String jsonColaboradoresTabla) {
		try {
			List<Colaborador> colaboradoresSeleccionados = new ArrayList<Colaborador>();
			List<ColaboradorProyecto> colaboradoresProyectoAdd = new ArrayList<ColaboradorProyecto>();
			List<ColaboradorProyecto> colaboradoresProyectoRemove = new ArrayList<ColaboradorProyecto>();
			Rol rol;
			Colaborador colaborador;
			if (jsonColaboradoresTabla != null && !jsonColaboradoresTabla.equals("")) {
				colaboradoresSeleccionados = JsonUtil.mapJSONToArrayList(jsonColaboradoresTabla, Colaborador.class);
			}
			for (Colaborador c : colaboradoresSeleccionados) {
				System.out.println("CURP: "+c.getCurp());
			}
			for (ColaboradorProyecto colaboradorProyectoOld : model.getProyecto_colaboradores()) {
				if (colaboradorProyectoOld.getRol().getId() != Constantes.ROL_LIDER && !isContained(colaboradorProyectoOld, colaboradoresSeleccionados)){
					colaboradoresProyectoRemove.add(colaboradorProyectoOld);
				}
			}
			for (Colaborador colaboradorSeleccionado : colaboradoresSeleccionados) {
				if (!isContained(colaboradorSeleccionado, model.getProyecto_colaboradores())) {
					rol = genericoDAO.findById(Rol.class, Constantes.ROL_ANALISTA);
					colaborador = genericoDAO.findById(Colaborador.class, colaboradorSeleccionado.getCurp());
					colaboradoresProyectoAdd.add(new ColaboradorProyecto(colaborador, rol, model));
				}
			}
			genericoDAO.deleteList(colaboradoresProyectoRemove);
			genericoDAO.updateList(colaboradoresProyectoAdd);
		} catch (Exception e) {
			throw new TESSERACTException("No se puede editar colaboradores del proyecto.", "MSG12");
		}
	}
	
	private boolean isContained(ColaboradorProyecto colaboradorProyectoOld, List<Colaborador> colaboradoresSeleccionados) {
		Boolean isValid = false;
		for (Colaborador colaborador : colaboradoresSeleccionados) {
			if (colaborador.getCurp().equals(colaboradorProyectoOld.getColaborador().getCurp())) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}

	private boolean isContained(Colaborador colaborador, List<ColaboradorProyecto> colaboradores) {
		Boolean isValid = false;
		for (ColaboradorProyecto colaboradorProyecto : colaboradores) {
			if (colaboradorProyecto.getColaborador().getCurp().equals(colaborador.getCurp())) {
				isValid = true;
			}
		}
		return isValid;
	}

}

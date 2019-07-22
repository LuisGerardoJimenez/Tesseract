package mx.tesseract.admin.bs;

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
			throw new TESSERACTException("No se puede consultar el proyecto.", "MSG13");
		}
		return proyecto;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarProyecto(Proyecto model) {
		if (rn022.isValidRN022(model)) {
			if (rn006.isValidRN006(model)) {
				if (rn035.isValidRN035(model.getFechaInicioProgramada(), model.getFechaTerminoProgramada())) {
					agregarLider(model);
					genericoDAO.save(model);
					genericoDAO.saveList(model.getProyecto_colaboradores());
				} else {
					throw new TESSERACTValidacionException("El usuario ingresó en desorden las fechas.", "MSG35",
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
					agregarLider(model);
					System.out.println("--------------------------------------");
					System.out.println("Ya se va a modificar");
					for (ColaboradorProyecto cp : model.getProyecto_colaboradores()) {
						System.out.println("Nombre: "+cp.getColaborador().getNombre());
						System.out.println("Rol: "+cp.getRol().getNombre());
					}
					System.out.println("--------------------------------------");
					genericoDAO.update(model);
					genericoDAO.updateList(model.getProyecto_colaboradores());
				} else {
					throw new TESSERACTValidacionException("El usuario ingresó en desorden las fechas.", "MSG35",
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
			genericoDAO.eliminar(model);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG14");
		}
	}

	private void agregarLider(Proyecto model) {
		ColaboradorProyecto lider = null;
		ColaboradorProyecto colaboradorproyecto = null;
		Colaborador seleccionado = colaboradorDAO.findColaboradorByCURP(model.getColaboradorCurp());
		Rol rol = genericoDAO.findById(Rol.class, Constantes.ROL_LIDER);
		if (model.getProyecto_colaboradores().size() < Constantes.NUMERO_UNO) {
			colaboradorproyecto = new ColaboradorProyecto(seleccionado, rol, model);
			model.getProyecto_colaboradores().add(colaboradorproyecto);
		} else {
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
			} else {
				if (lider.getId() != colaboradorproyecto.getId()) {
					colaboradorproyecto.setRol(rol);
					model.getProyecto_colaboradores().remove(lider);
				}
			}
		}
	}
//
//	public static ColaboradorProyecto consultarColaboradorProyectoLider(Proyecto model) {
//		Set<ColaboradorProyecto> colaboradores_proyecto = model.getProyecto_colaboradores();
//		int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
//		for(ColaboradorProyecto cp : colaboradores_proyecto) {
//			if(cp.getRol().getId() == idLider) {
//				return cp;
//			}
//		}
//		return null;
//	}

}

package mx.tesseract.admin.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.dao.ColaboradorDAO;
//import mx.tesseract.admin.dao.EstadoProyectoDAO;
import mx.tesseract.admin.dao.ProyectoDAO;
//import mx.tesseract.admin.dao.RolDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.admin.entidad.Rol;
import mx.tesseract.dao.GenericoDAO;
/*import mx.tesseract.bs.EstadoProyectoEnum;
import mx.tesseract.bs.RolBs;
import mx.tesseract.bs.RolBs.Rol_Enum;*/
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("proyectoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ProyectoBs {
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private ProyectoDAO proyectoDAO;

	public List<Proyecto> consultarProyectos() {
		List<Proyecto> proyectos = genericoDAO.findAll(Proyecto.class);
		if(proyectos.size() == Constantes.NUMERO_CERO) {
			throw new TESSERACTException("No se pueden consultar los proyectos.", "MSG13");
		}
		return proyectos;
	}
	
	public List<Proyecto> consultarProyectosByColaborador(String curp) {
		List<Proyecto> proyectos = proyectoDAO.findByCURPColaborador(curp);
		if (proyectos.size() == Constantes.NUMERO_CERO) {
			throw new TESSERACTException("No se pueden consultar los proyectos.", "MSG13");
		}
		return proyectos;
	}
	
	public Proyecto consultarProyecto(Integer idSel) {
		Proyecto proyecto = genericoDAO.findById(Proyecto.class, idSel);
		if(proyecto == null) {
			throw new TESSERACTException("No se puede consultar el proyecto.", "MSG13");
		}
		return proyecto;
	}

	/*public static void registrarProyecto(Proyecto model, String curpLider, int idEstadoProyecto, String presupuesto) throws Exception {
		try {
			validar(model, curpLider, idEstadoProyecto, presupuesto);
			ProyectoBs.agregarEstado(model, idEstadoProyecto);
			ProyectoBs.agregarLider(model, curpLider);
			new ProyectoDAO().registrarProyecto(model);
		} catch (JDBCException je) {
			if (je.getErrorCode() == 1062) {
				throw new TESSERACTValidacionException("El Proyecto"
						+ model.getClave() + " " + model.getNombre() + " ya existe.", "MSG7");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public static void modificarProyecto(Proyecto model, String curpLider, int idEstadoProyecto, String presupuestoString) throws Exception {
		try {
			validar(model, curpLider, idEstadoProyecto, presupuestoString);
			ProyectoBs.agregarEstado(model, idEstadoProyecto);
			ProyectoBs.agregarLider(model, curpLider);
			new ProyectoDAO().modificarProyecto(model);
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}

	}

	private static void validar(Proyecto model, String curpLider, int idEstadoProyecto, String presupuestoString) {
		//Validaciones campo obligatorio
		if (Validador.esNuloOVacio(model.getClave())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la clave del proyecto.", "MSG4",
					null, "model.clave");
		}
		if (Validador.esNuloOVacio(model.getNombre())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó el nombre del proyecto.", "MSG4",
					null, "model.nombre");
		}
		if (Validador.esNulo(model.getFechaInicioProgramada())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la fecha de inicio programada.", "MSG4",
					null, "model.fechaInicioProgramada");
		}
		if (Validador.esNulo(model.getFechaTerminoProgramada())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la fecha de término programada.", "MSG4",
					null, "model.fechaTerminoProgramada");
		}
		if(curpLider.equals(Constantes.NUMERO_UNO_NEGATIVO_STRING)) {
			throw new TESSERACTValidacionException("El usuario no seleccionó el lider del proyecto.", "MSG4", null, "curpLider");
		}
		if (Validador.esNuloOVacio(model.getDescripcion())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la descripción del proyecto.", "MSG4",
					null, "model.descripcion");
		}
		if (Validador.esNuloOVacio(model.getContraparte())) {
			throw new TESSERACTValidacionException(
					"El usuario no ingresó la contraparte del proyecto.", "MSG4",
					null, "model.contraparte");
		}
		if(idEstadoProyecto == Constantes.NUMERO_UNO_NEGATIVO) {
			throw new TESSERACTValidacionException("El usuario no seleccionó el estado del proyecto.", "MSG4", null, "idEstadoProyecto");
		}
		//Validaciones Longitud
		if (Validador.validaLongitudMaxima(model.getClave(), Constantes.NUMERO_DIEZ)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una clave muy larga.", "MSG6",
					new String[] { Constantes.NUMERO_DIEZ.toString(), "caracteres" },
					"model.clave");
		}
		if (Validador.validaLongitudMaxima(model.getNombre(), Constantes.NUMERO_CINCUENTA)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso un nombre muy largo.", "MSG6",
					new String[] { Constantes.NUMERO_CINCUENTA.toString(), "caracteres" },
					"model.nombre");
		}
		if (Validador.validaLongitudMaxima(model.getDescripcion(), Constantes.NUMERO_NOVECIENTOS_NOVENTA_Y_NUEVE)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una descripción muy larga.", "MSG6",
					new String[] { Constantes.NUMERO_NOVECIENTOS_NOVENTA_Y_NUEVE.toString(), "caracteres" }, "model.descripcion");
		}
		if (Validador.validaLongitudMaxima(model.getContraparte(), Constantes.NUMERO_CUARENTA_Y_CINCO)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una contraparte muy larga.", "MSG6",
					new String[] { Constantes.NUMERO_CUARENTA_Y_CINCO.toString(), "caracteres" }, "model.contraparte");
		}
		//Validaciones tipo de dato
		if (Validador.esInvalidaREGEX(model.getClave(), Constantes.REGEX_CAMPO_ALFANUMERICO_MAYUSCULAS_SIN_ESPACIOS)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una clave incorrecta.", "MSG50", null, "model.clave");
		}
		if (Validador.esInvalidaREGEX(model.getNombre(), Constantes.REGEX_CAMPO_ALFANUMERICO)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso un nombre incorrecto.", "MSG50", null, "model.nombre");
		}
		if (Validador.esInvalidaREGEX(model.getDescripcion(), Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una descripción incorrecta.", "MSG50", null, "model.descripcion");
		}
		if (Validador.esInvalidaREGEX(model.getContraparte(), Constantes.REGEX_CAMPO_ALFANUMERICO_CARACTERES_ESPECIALES)) {
			throw new TESSERACTValidacionException(
					"El usuario ingreso una contraparte incorrecta.", "MSG50", null, "model.contraparte");
		}
		if (!Validador.esNuloOVacio(presupuestoString)) {
			if (Validador.esInvalidaREGEX(presupuestoString, Constantes.REGEX_PRESUPUESTO)) {
				throw new TESSERACTValidacionException(
						"El usuario ingreso presupuesto incorrecto.", "MSG50", null, "presupuestoString");
			} else {
				model.setPresupuesto(Double.valueOf(presupuestoString));
			}
		}
		
		//Validaciones Negocio
		//Se asegura la unicidad del nombre y clave
		List<Proyecto> proyectosBD = new ProyectoDAO().consultarProyectos();
		for(Proyecto proy : proyectosBD) {
			if(model.getId() != proy.getId()) {
				if(model.getClave().equals(proy.getClave())) {
					throw new TESSERACTValidacionException(
							"La clave del proyecto ya existe.",
							"MSG7",
							new String[] { "El", "Proyecto", model.getClave() },
							"model.clave");
				}
				if(model.getNombre().equals(proy.getNombre())) {
					throw new TESSERACTValidacionException(
							"El nombre del proyecto ya existe.",
							"MSG7",
							new String[] { "El", "Proyecto", model.getNombre() },
							"model.nombre");
				}
			}
		}
		
		// Validaciones de las fechas
		// Se verifica nulidad para validar fechas en caso de haber ingresado un valor
		if (model.getFechaInicio() != null && model.getFechaTermino() != null && Validador.esInvalidoOrdenFechas(model.getFechaInicio(), model.getFechaTermino())) {
			throw new TESSERACTValidacionException(
					"El usuario ingresó en desorden las fechas.", "MSG35",
					new String[] { "fecha de término", "fecha de inicio" }, "model.fechaTermino");
		}
		// No se verifica nulidad, anteriormente se verificó
		if (Validador.esInvalidoOrdenFechas(model.getFechaInicioProgramada(), model.getFechaTerminoProgramada())) {
			throw new TESSERACTValidacionException(
					"El usuario ingresó en desorden las fechas.", "MSG35",
					new String[] { "fecha de término programada", "fecha de inicio programada" }, "model.fechaTerminoProgramada");
		}
	}
	
	public static void modificarColaboradoresProyecto(Proyecto model) throws Exception {
		try {
			new ProyectoDAO().modificarProyecto(model);
		} catch (JDBCException je) {
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static List<EstadoProyecto> consultarEstadosProyecto() {
		List<EstadoProyecto> estados = new EstadoProyectoDAO().consultarEstadosProyecto();
		if(estados == null) {
			throw new TESSERACTException("No se pueden consultar los estados de proyectos.",
					"MSG13");
		}
		return estados;
	}

	public static void eliminarProyecto(Proyecto model) throws Exception {
		try {
			new ProyectoDAO().eliminarProyecto(model);
			
		} catch (JDBCException je) {
			if(je.getErrorCode() == 1451)
			{
				throw new TESSERACTException("No se puede eliminar el proyecto.", "MSG14");
			}
			System.out.println("ERROR CODE " + je.getErrorCode());
			je.printStackTrace();
			throw new Exception();
		} catch(HibernateException he) {
			he.printStackTrace();
			throw new Exception();
		}
		
	}

	public static void agregarEstado(Proyecto model, int idEstadoProyecto) {
		EstadoProyecto estado = new EstadoProyectoDAO().consultarEstadoProyecto(idEstadoProyecto);
		model.setEstadoProyecto(estado);
	}

	public static void agregarLider(Proyecto proyecto, String curpLider) {
		Colaborador liderVista = new ColaboradorDAO().consultarColaborador(curpLider);
		ColaboradorProyecto colaboradorproyecto = null;
		ColaboradorProyecto lider = null;
		int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
		Rol rolLider = new RolDAO().consultarRol(idLider);
		
		for (ColaboradorProyecto colaborador : proyecto.getProyecto_colaboradores()) {
			if (colaborador.getRol().getId() == idLider) {
				lider = colaborador;
			}
			if (curpLider.equals(colaborador.getColaborador().getCurp())) {
				colaboradorproyecto = colaborador;
			}
		}
		
		for (ColaboradorProyecto colaborador : proyecto.getProyecto_colaboradores()) {
			if (curpLider.equals(colaborador.getColaborador().getCurp())) {
				colaboradorproyecto = colaborador;
			}
		}

		
		if (colaboradorproyecto == null) {
			colaboradorproyecto = new ColaboradorProyecto(liderVista, rolLider, proyecto);
			proyecto.getProyecto_colaboradores().add(colaboradorproyecto);
			proyecto.getProyecto_colaboradores().remove(lider);
			
		} else 
			if (lider.getId() == colaboradorproyecto.getId()){
				colaboradorproyecto.setRol(rolLider);
			} else {
				colaboradorproyecto.setRol(rolLider);
				proyecto.getProyecto_colaboradores().remove(lider);
		}
		
	}


	public static ColaboradorProyecto consultarColaboradorProyectoLider(Proyecto model) {
		Set<ColaboradorProyecto> colaboradores_proyecto = model.getProyecto_colaboradores();
		int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
		for(ColaboradorProyecto cp : colaboradores_proyecto) {
			if(cp.getRol().getId() == idLider) {
				return cp;
			}
		}
		return null;
	}

	public static List<EstadoProyecto> consultarEstadosProyectoRegistro() {
		List<EstadoProyecto> estadosAux = new EstadoProyectoDAO().consultarEstadosProyecto();
		List<EstadoProyecto> estados = new ArrayList<EstadoProyecto>();
		int idEstadoTerminado = EstadoProyectoEnum.consultarIdEstadoProyecto(EstadoProyectoEnum.EstadoProyecto.TERMINADO);
				
		if(estadosAux == null) {
			throw new TESSERACTException("No se pueden consultar los estados de proyectos.",
					"MSG13");
		}
		for(EstadoProyecto ep : estadosAux) {
			if(ep.getId() != idEstadoTerminado) {
				estados.add(ep);
			}
		}
		
		return estados;
	}*/
	
}

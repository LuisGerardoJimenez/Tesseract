package mx.tesseract.admin.bs;

import java.util.List;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.br.RN027;
import mx.tesseract.br.RN033;
import mx.tesseract.br.RN036;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.Correo;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("colaboradorBS")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ColaboradorBs {

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ColaboradorDAO colaboradorDAO;
	
	@Autowired
	private RN027 rn027;

	@Autowired
	private RN033 rn033;

	@Autowired
	private RN036 rn036;

	@Autowired
	private Correo correo;

	public List<Colaborador> consultarColaboradores() {
		List<Colaborador> colaboradores = colaboradorDAO.findAllWithoutAdmin();
		return colaboradores;
	}

	public List<Colaborador> consultarColaboradoresCatalogo() {
		List<Colaborador> colaboradores = colaboradorDAO.findAllWithoutAdmin();
		if (colaboradores.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar los colaboradores.", "MSG22",
					new String[] { "colaboradores" });
		}
		return colaboradores;
	}

	public Colaborador consultarPersona(String curp) {
		Colaborador colaborador = colaboradorDAO.findColaboradorByCURP(curp);
		if (colaborador == null) {
			throw new TESSERACTException("No se puede consultar el colaborador.", "MSG13");
		}
		return colaborador;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarColaborador(Colaborador model) {
		if (rn033.isValidRN033(model)) {
			if (rn036.isValidRN036(model)) {
				genericoDAO.save(model);
				enviarCorreo(model, null, null);
			} else {
				throw new TESSERACTValidacionException("El correo del colaborador ya existe.", "MSG7",
						new String[] { "El", "correo electrónico", model.getCorreoElectronico() },
						"model.correoElectronico");
			}
		} else {
			throw new TESSERACTValidacionException("La Persona con CURP" + model.getCurp() + " ya existe.", "MSG7",
					new String[] { "La", "persona con CURP", model.getCurp() }, "model.curp");
		}
	}

	public void enviarCorreo(Colaborador model, String correoAnterior, String contrasenaAnterior) {
		try {
			if (contrasenaAnterior == null || correoAnterior == null) {
				correo.enviarCorreo(model, Constantes.NUMERO_CERO);
				System.out.println("Se envió un correo al usuario que se registró.");
			} else if (!contrasenaAnterior.equals(model.getContrasenia())) {
				correo.enviarCorreo(model, Constantes.NUMERO_CERO);
				System.out.println("Se envió un correo porque cambió la contraseña.");
			} else if (!correoAnterior.equals(model.getCorreoElectronico())) {
				correo.enviarCorreo(model, Constantes.NUMERO_CERO);
				System.out.println("Se envió un correo porque cambio el correo electrónico.");
			}
		} catch (Exception e) {
			System.err.println("Error al enviar el Correo");
			e.printStackTrace();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarColaborador(Colaborador model, String correoAnterior, String contraseniaAnterior) {
		if (rn036.isValidRN036(model)) {
			genericoDAO.update(model);
			enviarCorreo(model, correoAnterior, contraseniaAnterior);
		} else {
			throw new TESSERACTValidacionException("El correo del colaborador ya existe.", "MSG7",
					new String[] { "El", "correo electrónico", model.getCorreoElectronico() },
					"model.correoElectronico");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void eliminarColaborador(Colaborador model) {
		if (rn027.isValidRN027(model)) {
			genericoDAO.delete(model);
		} else {
			System.out.println("Error en regla de negocio");
			throw new TESSERACTException("No se puede eliminar el colaborador porque ya esta asoaciado a un proyecto", "MSG55");
		}
	}

//	public static List<String> verificarProyectosLider(Colaborador model) {
//		int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
//		List<String> proyectos = new ArrayList<String>();
//		Set<String> setProyectos = new HashSet<String>(0);
//
//		List<ColaboradorProyecto> colaboradoresProyecto = null;
//		colaboradoresProyecto = new ColaboradorProyectoDAO().consultarLiderColaboradoresProyecto(model);
//
//		for (ColaboradorProyecto cp : colaboradoresProyecto) {
//			if (cp.getRol().getId() == idLider) {
//				String linea = "";
//				String proyecto = cp.getProyecto().getClave() + " " + cp.getProyecto().getNombre();
//				linea = "Esta persona es líder del Proyecto " + proyecto + ".";
//				setProyectos.add(linea);
//			}
//		}
//
//		proyectos.addAll(setProyectos);
//		return proyectos;
//	}

//
//public static boolean esLiderProyecto(Colaborador model) {
//	Set<ColaboradorProyecto> colaboradoresProyecto = model.getColaborador_proyectos();
//	int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
//	for(ColaboradorProyecto cp : colaboradoresProyecto) {
//		if(cp.getRol().getId() == idLider) {
//			return true;
//		}
//	}
//	return false;
//}
//
//public static List<String> verificarProyectosLider(Colaborador model) {
//	int idLider = RolBs.consultarIdRol(Rol_Enum.LIDER);
//	List<String> proyectos = new ArrayList<String>();
//	Set<String> setProyectos = new HashSet<String>(0);
//	
//	List<ColaboradorProyecto> colaboradoresProyecto = null;
//	colaboradoresProyecto = new ColaboradorProyectoDAO().consultarLiderColaboradoresProyecto(model);
//	
//	for(ColaboradorProyecto cp : colaboradoresProyecto) {
//		if(cp.getRol().getId() == idLider) {
//			String linea = "";
//			String proyecto = cp.getProyecto().getClave() + " " + cp.getProyecto().getNombre();
//			linea = "Esta persona es líder del Proyecto " + proyecto + ".";
//			setProyectos.add(linea);
//		}
//	}
//	
//	proyectos.addAll(setProyectos);
//	return proyectos;
//}
}

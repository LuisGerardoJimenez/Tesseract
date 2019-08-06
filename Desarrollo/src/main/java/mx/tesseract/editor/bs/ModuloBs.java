package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.bs.ProyectoBs;
import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN023;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.ModuloDAO;
import mx.tesseract.editor.entidad.Modulo;
//import mx.tesseract.editor.dao.CasoUsoActorDAO;
//import mx.tesseract.editor.dao.ModuloDAO;
//import mx.tesseract.editor.dao.ReferenciaParametroDAO;
//import mx.tesseract.editor.model.Actor;
//import mx.tesseract.editor.model.CasoUso;
//import mx.tesseract.editor.model.CasoUsoActor;
import mx.tesseract.editor.entidad.Modulo;
//import mx.tesseract.editor.model.Pantalla;
//import mx.tesseract.editor.model.Paso;
//import mx.tesseract.editor.model.PostPrecondicion;
//import mx.tesseract.editor.model.ReferenciaParametro;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.SessionManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.Validador;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
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
		return modulo;
	}

//	public static Modulo consultarModulo(int idActor) {
//		Modulo modulo = null;
//		modulo = new ModuloDAO()
//				.consultarModulo(idActor);
//		if (modulo == null) {
//			throw new TESSERACTException(
//					"No se pueden consultar los módulos.",
//					"MSG12");
//		}
//		return modulo;
//	}
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

//
//	private static void validar(Modulo model) {
//		//Validaciones Negocio
//		//Se asegura la unicidad del nombre y clave
//		List<Modulo> modulosBD = consultarModulosProyecto(model.getProyecto());
//		for(Modulo modulo : modulosBD) {
//			if(model.getId() != modulo.getId()) {
//				if(model.getClave().equals(modulo.getClave())) {
//					throw new TESSERACTValidacionException(
//							"La clave del módulo ya existe.",
//							"MSG7",
//							new String[] { "El", "módulo", model.getClave() },
//							"model.clave");
//				}
//				if(model.getNombre().equals(modulo.getNombre())) {
//					throw new TESSERACTValidacionException(
//							"El nombre del módulo ya existe.",
//							"MSG7",
//							new String[] { "El", "módulo", model.getNombre() },
//							"model.nombre");
//				}
//			}
//		}
//	}

//	public static void eliminarTermino(Modulo model) throws Exception {
//		try {
//			new ModuloDAO().eliminarModulo(model);
//		} catch (JDBCException je) {
//			System.out.println("ERROR CODE " + je.getErrorCode());
//			je.printStackTrace();
//			throw new Exception();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			throw new Exception();
//		}
//
//	}
//
//
//	public static void modificarModulo(Modulo model) throws Exception {
//		try {
//			validar(model);
//			new ModuloDAO().modificarModulo(model);
//		} catch (JDBCException je) {
//			System.out.println("ERROR CODE " + je.getErrorCode());
//			je.printStackTrace();
//			throw new Exception();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			throw new Exception();
//		}
//		
//	}
//
//	public static void eliminarModulo(Modulo model) {
//		new ModuloDAO().eliminarModulo(model);
//		
//	}
//
//	public static List<String> verificarReferencias(Modulo model) {
//
//		System.out.println("entramos a verificar Referencias");
//		List<String> listReferenciasVista = new ArrayList<String>();
//		Set<String> setReferenciasVista = new HashSet<String>(0);
//		
//		List<CasoUso> casosUso = CuBs.consultarCasosUsoModulo(model);
//		List<Pantalla> pantallas = PantallaBs.consultarPantallasModulo(model);
//		System.out.println("Se buscan los CU y pantallas");
//		for(CasoUso casoUso : casosUso) {
//			setReferenciasVista.addAll(CuBs.verificarReferencias(casoUso, model));
//		}
//		
//		for(Pantalla pantalla : pantallas) {
//			setReferenciasVista.addAll(PantallaBs.verificarReferencias(pantalla, model));
//		}
//		
//
//		listReferenciasVista.addAll(setReferenciasVista);
//
//		return listReferenciasVista;
//	}

}

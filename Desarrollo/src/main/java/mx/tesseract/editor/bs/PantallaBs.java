package mx.tesseract.editor.bs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN001;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN034;
import mx.tesseract.br.RN040;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.enums.ReferenciaEnum;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.editor.dao.PantallaDAO;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.util.ImageConverterUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("pantallasBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class PantallaBs {
	
	@Autowired
	private PantallaDAO pantallaDAO;
	
	@Autowired
	private RN040 rn040;
	
	@Autowired
	private RN001 rn001;
	
	@Autowired
	private RN006 rn006;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private ElementoBs elementoBs;

	public  List<Pantalla> consultarPantallasByModulo(Integer idProyecto, Integer idModulo) {
		List<Pantalla> listPantallas = pantallaDAO.findAllByIdModulo(idProyecto, Clave.IU, idModulo);
		return listPantallas;
	}
	
	public PantallaDTO consultarPantalla(Integer idSel) {
		Pantalla pantalla = genericoDAO.findById(Pantalla.class, idSel);
		PantallaDTO pantallaDTO = new PantallaDTO();
		if (pantalla != null) {
			pantallaDTO.setId(pantalla.getId());
			pantallaDTO.setClave(pantalla.getClave());
			pantallaDTO.setNumero(pantalla.getNumero());
			pantallaDTO.setNombre(pantalla.getNombre());
			pantallaDTO.setDescripcion(pantalla.getDescripcion());
			pantallaDTO.setPantallaB64(pantalla.getImagen());
		}
		return pantallaDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarPantalla(PantallaDTO pantallaDTO, File archivo) {
		if (archivo != null) {
			if (rn040.isValidRN040(archivo)) {
				validarYAgregar(pantallaDTO, archivo);
			} else {
				throw new TESSERACTValidacionException("Archivo muy grande.", "MSG17", 
						new String[] { "2", "MB" }, null);
			}
		} else {
			throw new TESSERACTValidacionException("Seleccione una imagen.", "MSG30", null, "imagenPantalla");
		}
	}
	
	private void validarYAgregar(PantallaDTO pantallaDTO, File archivo) {
		if (rn001.isValidRN001(pantallaDTO)) {
			if (rn006.isValidRN006(pantallaDTO)) {
				System.out.println("Se va a guardar");
				Pantalla pantalla = new Pantalla();
				Proyecto proyecto = genericoDAO.findById(Proyecto.class, pantallaDTO.getIdProyecto());
				byte[] imagen = ImageConverterUtil.parseFileToBASE64ByteArray(archivo);
				Modulo modulo = genericoDAO.findById(Modulo.class, pantallaDTO.getIdModulo());
				pantalla.setClave(Clave.IU.toString());
				pantalla.setNumero(pantallaDTO.getNumero());
				pantalla.setNombre(pantallaDTO.getNombre());
				pantalla.setDescripcion(pantallaDTO.getDescripcion());
				pantalla.setProyecto(proyecto);
				pantalla.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
				pantalla.setImagen(imagen);
				pantalla.setModulo(modulo);
				genericoDAO.save(pantalla);
			} else {
				throw new TESSERACTValidacionException("EL nombre de la pantalla ya existe.", "MSG7",
						new String[] { "La", "Pantalla", pantallaDTO.getNombre() }, "model.nombre");
			}
		} else {
			throw new TESSERACTValidacionException("EL número de la pantalla ya existe.", "MSG7",
					new String[] { "El", "número", pantallaDTO.getNumero() }, "model.numero");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modificarPantalla(PantallaDTO pantallaDTO, File archivo) {
		if (archivo != null) {
			if (rn040.isValidRN040(archivo)) {
				validarYEditar(pantallaDTO, archivo, true);
			} else {
				throw new TESSERACTValidacionException("Archivo muy grande.", "MSG17", 
						new String[] { "2", "MB" }, null);
			}
		} else {
			validarYEditar(pantallaDTO, archivo, false);
		}
	}
	
	private void validarYEditar(PantallaDTO pantallaDTO, File archivo, Boolean cambiarImagen) {
		if (rn001.isValidRN001(pantallaDTO)) {
			if (rn006.isValidRN006(pantallaDTO)) {
				System.out.println("Se va a editar");
				Pantalla pantalla = genericoDAO.findById(Pantalla.class, pantallaDTO.getId());
				if (cambiarImagen) {
					byte[] imagen = ImageConverterUtil.parseFileToBASE64ByteArray(archivo);
					pantalla.setImagen(imagen);
				}
				pantalla.setNumero(pantallaDTO.getNumero());
				pantalla.setNombre(pantallaDTO.getNombre());
				pantalla.setDescripcion(pantallaDTO.getDescripcion());
				genericoDAO.update(pantalla);
			} else {
				throw new TESSERACTValidacionException("EL nombre de la pantalla ya existe.", "MSG7",
						new String[] { "La", "Pantalla", pantallaDTO.getNombre() }, "model.nombre");
			}
		} else {
			throw new TESSERACTValidacionException("EL número de la pantalla ya existe.", "MSG7",
					new String[] { "El", "número", pantallaDTO.getNumero() }, "model.numero");
		}
	}

//	public static List<TipoAccion> consultarTiposAccion() {
//		List<TipoAccion> listTiposAccion = new TipoAccionDAO()
//				.consultarTiposAccion();
//		if (listTiposAccion == null) {
//			throw new TESSERACTException(
//					"No se pueden consultar los tipos de acción.", "MSG13");
//		}
//		return listTiposAccion;
//	}

//	public static List<Pantalla> consultarPantallasProyecto(Proyecto proyecto) {
//		List<Elemento> listPantallasAux = new PantallaDAO().consultarElementos(
//				ReferenciaEnum.TipoReferencia.PANTALLA, proyecto.getId());
//		List<Pantalla> listPantallas = new ArrayList<Pantalla>();
//		if (listPantallasAux == null) {
//			throw new TESSERACTException(
//					"No se pueden consultar las pantallas por proyecto.",
//					"MSG13");
//		}
//		for (Elemento elem : listPantallasAux) {
//			listPantallas.add((Pantalla) elem);
//		}
//
//		return listPantallas;
//	}

//	public static TipoAccion consultarTipoAccion(Integer id) {
//		TipoAccion ta = new TipoAccionDAO().consultarTipoAccion(id);
//		if (ta == null) {
//			throw new TESSERACTException(
//					"No se puede consultar el tipo de accion por el id.",
//					"MSG13");
//		}
//		return ta;
//	}
//
//	public static void eliminarPantalla(Pantalla model) throws Exception {
//		try {
//			ElementoBs.verificarEstado(model, CU_Pantallas.ELIMINARPANTALLA6_3);
//			new PantallaDAO().eliminarElemento(model);
//		} catch (JDBCException je) {
//			if (je.getErrorCode() == 1451) {
//				throw new TESSERACTException("No se puede eliminar la pantalla",
//						"MSG14");
//			}
//			System.out.println("ERROR CODE " + je.getErrorCode());
//			je.printStackTrace();
//			throw new Exception();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			throw new Exception();
//		}
//
//	}

//	public static List<String> verificarReferencias(Pantalla model, Modulo modulo) {
//
//		List<ReferenciaParametro> referenciasParametro = new ArrayList<ReferenciaParametro>();
//		List<Accion> referenciasAccion = new ArrayList<Accion>();
//
//		List<String> listReferenciasVista = new ArrayList<String>();
//		Set<String> setReferenciasVista = new HashSet<String>(0);
//
//		PostPrecondicion postPrecondicion = null; // Origen de la referencia
//		Paso paso = null; // Origen de la referencia
//		Accion accion = null; // Origen de la referencia
//		String casoUso = ""; // Caso de uso que tiene la referencia
//		String pantalla = ""; // Pantalla que tiene la referencia
//
//		referenciasParametro = new ReferenciaParametroDAO()
//				.consultarReferenciasParametro(model);
//		referenciasAccion = new AccionDAO().consultarReferencias(model);
//
//		for (ReferenciaParametro referencia : referenciasParametro) {
//			String linea = "";
//			postPrecondicion = referencia.getPostPrecondicion();
//			paso = referencia.getPaso();
//			accion = referencia.getAccionDestino();
//
//			if (postPrecondicion != null && (modulo == null || postPrecondicion.getCasoUso().getModulo().getId() != modulo.getId())) {
//				casoUso = postPrecondicion.getCasoUso().getClave()
//						+ postPrecondicion.getCasoUso().getNumero() + " "
//						+ postPrecondicion.getCasoUso().getNombre();
//				if (postPrecondicion.isPrecondicion()) {
//					linea = "Precondiciones del caso de uso " + casoUso;
//				} else {
//					linea = "Postcondiciones del caso de uso "
//							+ postPrecondicion.getCasoUso().getClave()
//							+ postPrecondicion.getCasoUso().getNumero() + " "
//							+ postPrecondicion.getCasoUso().getNombre();
//				}
//
//			} else if (paso != null && (modulo == null || paso.getTrayectoria().getCasoUso().getModulo().getId() != modulo.getId())) {
//				casoUso = paso.getTrayectoria().getCasoUso().getClave()
//						+ paso.getTrayectoria().getCasoUso().getNumero() + " "
//						+ paso.getTrayectoria().getCasoUso().getNombre();
//				linea = "Paso "
//						+ paso.getNumero()
//						+ " de la trayectoria "
//						+ ((paso.getTrayectoria().isAlternativa()) ? "alternativa "
//								+ paso.getTrayectoria().getClave()
//								: "principal") + " del caso de uso " + casoUso;
//			} else if (accion != null && (modulo == null || accion.getPantalla().getModulo().getId() != modulo.getId())) {
//				if (accion.getPantalla() != model) {
//					pantalla = accion.getPantalla().getClave()
//							+ accion.getPantalla().getNumero() + " "
//							+ accion.getPantalla().getNombre();
//					linea = "Acción " + accion.getNombre() + " de la pantalla "
//							+ pantalla;
//				}
//			}
//
//			if (linea != "") {
//				setReferenciasVista.add(linea);
//			}
//		}
//		for(Accion accionOP : referenciasAccion) {
//			if(modulo == null || accionOP.getPantalla().getModulo().getId() != modulo.getId()) {
//				String linea = "";
//				pantalla = accionOP.getPantalla().getClave()
//						+ accionOP.getPantalla().getNumero() + " "
//						+ accionOP.getPantalla().getNombre();
//				linea = "Acción " + accionOP.getNombre() + " de la pantalla "
//						+ pantalla; 
//				if (linea != "") {
//					setReferenciasVista.add(linea);
//				}
//			}
//		}
//
//		for (Accion acc : model.getAcciones()) {
//			setReferenciasVista.addAll(AccionBs.verificarReferencias(acc, modulo));
//		}
//
//		listReferenciasVista.addAll(setReferenciasVista);
//
//		return listReferenciasVista;
//	}
//	
//	public static List<CasoUso> verificarCasosUsoReferencias(Pantalla model) {
//
//		List<ReferenciaParametro> referenciasParametro = new ArrayList<ReferenciaParametro>();
//
//		List<CasoUso> listReferenciasVista = new ArrayList<CasoUso>();
//		Set<CasoUso> setReferenciasVista = new HashSet<CasoUso>(0);
//
//		PostPrecondicion postPrecondicion = null; // Origen de la referencia
//		Paso paso = null; // Origen de la referencia
//		Accion accion = null; // Origen de la referencia
//		String casoUso = ""; // Caso de uso que tiene la referencia
//		String pantalla = ""; // Pantalla que tiene la referencia
//
//		referenciasParametro = new ReferenciaParametroDAO()
//				.consultarReferenciasParametro(model);
//
//		for (ReferenciaParametro referencia : referenciasParametro) {
//			String linea = "";
//			postPrecondicion = referencia.getPostPrecondicion();
//			paso = referencia.getPaso();
//			accion = referencia.getAccionDestino();
//
//			if (postPrecondicion != null) {
//				setReferenciasVista.add(postPrecondicion.getCasoUso());
//
//			} else if (paso != null) {
//				setReferenciasVista.add(paso.getTrayectoria().getCasoUso());
//			}
//		}
//
//		for (Accion acc : model.getAcciones()) {
//			setReferenciasVista.addAll(AccionBs.verificarCasosUsoReferencias(acc));
//		}
//
//		listReferenciasVista.addAll(setReferenciasVista);
//
//		return listReferenciasVista;
//	}

//	public static void modificarPantalla(Pantalla model) throws Exception {
//		try {
//			validar(model);
//			ElementoBs
//					.verificarEstado(model, CU_Pantallas.MODIFICARPANTALLA6_2);
//			model.setClave(CLAVE + model.getModulo().getClave());
//			model.setEstadoElemento(ElementoBs
//					.consultarEstadoElemento(Estado.EDICION));
//			model.setNombre(model.getNombre().trim());
//
//			new PantallaDAO().modificarPantalla(model);
//
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
//	public static void modificarPatronPantalla(Pantalla pantalla, boolean validarObligatorios) {
//		String patron = pantalla.getPatron();
//		if (validarObligatorios && Validador.esNuloOVacio(patron)) {
//			throw new TESSERACTValidacionException(
//					"El usuario no ingresó algún patrón de pantalla.", "MSG38", null,
//					"campos");
//		}
//		if (Validador.validaLongitudMaxima(patron, 999)) {
//			throw new TESSERACTValidacionException(
//					"El usuario ingreso un patrón de pantalla muy largo.", "MSG39",
//					new String[] { "999", "caracteres", "el patrón de la pantalla " + pantalla.getClave() + pantalla.getNumero() + " " + pantalla.getNombre() }, "campos");
//		}
//		
//		new PantallaDAO().modificarPantalla(pantalla);
//	}
}

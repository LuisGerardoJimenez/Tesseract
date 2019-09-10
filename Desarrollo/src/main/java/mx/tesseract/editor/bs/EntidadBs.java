package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.EntidadDTO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
//import mx.tesseract.bs.CatalogoBs;
//import mx.tesseract.bs.AnalisisEnum.CU_CasosUso;
//import mx.tesseract.bs.AnalisisEnum.CU_Entidades;
//import mx.tesseract.bs.ReferenciaEnum.TipoCatalogo;
//import mx.tesseract.editor.bs.ElementoBs.Estado;
//import mx.tesseract.editor.dao.EntidadDAO;
//import mx.tesseract.editor.dao.ReferenciaParametroDAO;
//import mx.tesseract.editor.dao.TipoDatoDAO;
//import mx.tesseract.editor.dao.UnidadTamanioDAO;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Elemento;
//import mx.tesseract.editor.model.CasoUso;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.TerminoGlosario;
//import mx.tesseract.editor.model.Paso;
//import mx.tesseract.editor.model.PostPrecondicion;
//import mx.tesseract.editor.entidad.ReferenciaParametro;
import mx.tesseract.editor.entidad.TipoDato;
import mx.tesseract.editor.entidad.UnidadTamanio;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
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
import org.springframework.transaction.annotation.Transactional;

@Service("entidadBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class EntidadBs {
	
	@Autowired
	private RN006 rn006;
	
	@Autowired
	private ElementoDAO elementoDAO;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private ElementoBs elementoBs;
	
	@Autowired
	private RN018 rn018;
	
	public List<Entidad> consultarEntidadesProyecto(Integer idProyecto) {
		List<Entidad> listEntidades = elementoDAO.findAllByIdProyectoAndClave(Entidad.class, idProyecto, Clave.ENT);
		return listEntidades;
	}
	
	public EntidadDTO consultarEntidadDTOById(Integer id) {
		Entidad entidad = genericoDAO.findById(Entidad.class, id);
		EntidadDTO entidadDTO = new EntidadDTO();
		if (entidad != null) {
			entidadDTO.setId(entidad.getId());
			entidadDTO.setNombre(entidad.getNombre());
			entidadDTO.setDescripcion(entidad.getDescripcion());
			entidadDTO.setIdProyecto(entidad.getProyecto().getId());
		} else {
			throw new TESSERACTException("No se puede consultar la Entidad.", "MSG12");
		}
		return entidadDTO;
	}
	
	public Entidad consultarEntidadById(Integer id) {
		Entidad entidad = genericoDAO.findById(Entidad.class, id);
		if (entidad == null) {
			throw new TESSERACTException("No se puede consultar la Entidad.", "MSG12");
		}
		return entidad;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarEntidad(EntidadDTO entidadDTO) {
		if (rn006.isValidRN006(entidadDTO)) {
			Entidad entidad = new Entidad();
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, entidadDTO.getIdProyecto());
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.ENT);
			entidad.setClave(Clave.ENT.toString());
			entidad.setNumero(numero);
			entidad.setNombre(entidadDTO.getNombre());
			entidad.setDescripcion(entidadDTO.getDescripcion());
			entidad.setProyecto(proyecto);
			entidad.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			genericoDAO.save(entidad);
		} else {
			throw new TESSERACTValidacionException("EL nombre del término ya existe.", "MSG7",
					new String[] { "La", "Entidad", entidadDTO.getNombre() }, "model.nombre");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modificarEntidad(EntidadDTO entidadDTO) {
		System.out.println("ID Entidad: "+entidadDTO.getId());
		if (rn006.isValidRN006(entidadDTO)) {
			Entidad entidad = genericoDAO.findById(Entidad.class, entidadDTO.getId());
			entidad.setNombre(entidadDTO.getNombre());
			entidad.setDescripcion(entidadDTO.getDescripcion());
			genericoDAO.update(entidad);
		} else {
			throw new TESSERACTValidacionException("EL nombre del término ya existe.", "MSG7",
					new String[] { "La", "Entidad", entidadDTO.getNombre() }, "model.nombre");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void eliminarEntidad(EntidadDTO model) {
		if (rn018.isValidRN018(model)) {
			Entidad entidad = genericoDAO.findById(Entidad.class, model.getId());
			genericoDAO.delete(entidad);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}
	
//
//	public static List<Entidad> consultarEntidadesProyectoConFecha(
//			Proyecto proyecto) {
//		List<Entidad> listEntidadesAux = new EntidadDAO()
//				.consultarEntidades(proyecto.getId());
//		List<Entidad> listEntidades = new ArrayList<Entidad>();
//		for (Entidad entidad : listEntidadesAux) {
//			if (contieneAtributoTipoFecha(entidad)) {
//				listEntidades.add(entidad);
//			}
//		}
//		return listEntidades;
//	}
//
//	private static boolean contieneAtributoTipoFecha(Entidad entidad) {
//		Set<Atributo> atributos = entidad.getAtributos();
//		for (Atributo atributo : atributos) {
//			if (atributo.getTipoDato().getNombre().equals("Fecha")) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public static List<Atributo> consultarAtributosTipoFecha(int idEntidad) {
//		Set<Atributo> atributos = new EntidadDAO().consultarEntidad(idEntidad)
//				.getAtributos();
//		List<Atributo> listAtributos = new ArrayList<Atributo>();
//		for (Atributo atributo : atributos) {
//			if (atributo.getTipoDato().getNombre().equals("Fecha")) {
//				listAtributos.add(atributo);
//			}
//		}
//		return listAtributos;
//	}
//	
//	public static List<String> verificarReferencias(Entidad model) {
//
//		List<ReferenciaParametro> referenciasParametro;
//
//		List<String> listReferenciasVista = new ArrayList<String>();
//		Set<String> setReferenciasVista = new HashSet<String>(Constantes.NUMERO_CERO);
//		PostPrecondicion postPrecondicion = null;
//		Paso paso = null;
//
//		String casoUso = "";
//
//		referenciasParametro = new ReferenciaParametroDAO()
//				.consultarReferenciasParametro(model);
//
//		for (ReferenciaParametro referencia : referenciasParametro) {
//			String linea = "";
//			postPrecondicion = referencia.getPostPrecondicion();
//			paso = referencia.getPaso();
//
//			if (postPrecondicion != null) {
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
//			} else if (paso != null) {
//				casoUso = paso.getTrayectoria().getCasoUso().getClave()
//						+ paso.getTrayectoria().getCasoUso().getNumero() + " "
//						+ paso.getTrayectoria().getCasoUso().getNombre();
//				linea = "Paso "
//						+ paso.getNumero()
//						+ " de la trayectoria "
//						+ ((paso.getTrayectoria().isAlternativa()) ? "alternativa "
//								+ paso.getTrayectoria().getClave()
//								: "principal") + " del caso de uso " + casoUso;
//			}
//			
//			if (linea != "") {
//				setReferenciasVista.add(linea);
//			}
//		}
//
//		for (Atributo atributo : model.getAtributos()) {
//			setReferenciasVista.addAll(AtributoBs
//					.verificarReferencias(atributo));
//		}
//
//		listReferenciasVista.addAll(setReferenciasVista);
//
//		return listReferenciasVista;
//	}
//	
//	public static List<CasoUso> verificarCasosUsoReferencias(Entidad model) {
//
//		List<ReferenciaParametro> referenciasParametro;
//
//		List<CasoUso> listReferenciasVista = new ArrayList<CasoUso>();
//		Set<CasoUso> setReferenciasVista = new HashSet<CasoUso>(Constantes.NUMERO_CERO);
//		PostPrecondicion postPrecondicion = null;
//		Paso paso = null;
//
//		referenciasParametro = new ReferenciaParametroDAO()
//				.consultarReferenciasParametro(model);
//
//		for (ReferenciaParametro referencia : referenciasParametro) {
//			postPrecondicion = referencia.getPostPrecondicion();
//			paso = referencia.getPaso();
//
//			if (postPrecondicion != null) {
//				setReferenciasVista.add(postPrecondicion.getCasoUso());
//
//			} else if (paso != null) {
//				setReferenciasVista.add(paso.getTrayectoria().getCasoUso());
//			}
//		}
//
//		for (Atributo atributo : model.getAtributos()) {
//			setReferenciasVista.addAll(AtributoBs
//					.verificarCasosUsoReferencias(atributo));
//		}
//
//		listReferenciasVista.addAll(setReferenciasVista);
//
//		return listReferenciasVista;
//	}
//
//	public static void modificarEntidad(Entidad model,
//			Actualizacion actualizacion) throws Exception {
//		try {
//			validar(model);
//			ElementoBs
//					.verificarEstado(model, CU_Entidades.MODIFICARENTIDAD11_2);
//			model.setEstadoElemento(ElementoBs
//					.consultarEstadoElemento(Estado.EDICION));
//			model.setNombre(model.getNombre().trim());
//
//			new EntidadDAO().modificarEntidad(model, actualizacion);
//
//		} catch (JDBCException je) {
//			System.out.println("ERROR CODE " + je.getErrorCode());
//			je.printStackTrace();
//			throw new Exception();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			throw new Exception();
//		}
//	}
}

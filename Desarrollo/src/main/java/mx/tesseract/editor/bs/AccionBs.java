package mx.tesseract.editor.bs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.br.RN006;
import mx.tesseract.br.RN040;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.AccionDTO;
import mx.tesseract.editor.dao.AccionDAO;
import mx.tesseract.editor.dao.ReferenciaParametroDAO;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.editor.entidad.ReferenciaParametro;
import mx.tesseract.editor.entidad.TipoAccion;
import mx.tesseract.util.ImageConverterUtil;
import mx.tesseract.util.TESSERACTValidacionException;

@Service("accionBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AccionBs {

	@Autowired
	private AccionDAO accionDAO;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private RN040 rn040;
	
	@Autowired
	private RN006 rn006;
	
	@Autowired
	private ReferenciaParametroDAO referenciaParametroDAO;
	
	public List<Accion> consultarAccionesByPantalla(Integer idPantalla) {
		List<Accion> acciones = accionDAO.findAllByPantalla(idPantalla);
		return acciones;
	}
	
	public AccionDTO consultarAccionDTO(Integer idAccion) {
		AccionDTO accionDTO = new AccionDTO();
		try {
			Accion accion = genericoDAO.findById(Accion.class, idAccion);
			if (accion != null) {
				accionDTO.setId(accion.getId());
				accionDTO.setNombre(accion.getNombre());
				accionDTO.setDescripcion(accion.getDescripcion());
				accionDTO.setIdPantalla(accion.getPantalla().getId());
				accionDTO.setIdTipoAccion(accion.getTipoAccion().getId());
				accionDTO.setIdPantallaDestino(accion.getPantallaDestino().getId());
				accionDTO.setImagenB64(ImageConverterUtil.parseBytesToPNGB64String(accion.getImagen()));
			}
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
		return accionDTO;
	}
	
	public List<AccionDTO> consultarAccionesDTOByPantalla(Integer idPantalla) {
		List<AccionDTO> accionesDTO = new ArrayList<AccionDTO>();
		try {
			List<Accion> acciones = accionDAO.findAllByPantalla(idPantalla);
			for (Accion accion : acciones) {
				AccionDTO accionDTO = new AccionDTO();
				accionDTO.setId(accion.getId());
				accionDTO.setNombre(accion.getNombre());
				accionDTO.setDescripcion(accion.getDescripcion());
				accionDTO.setIdPantallaDestino(accion.getPantalla().getId());
				accionDTO.setIdTipoAccion(accion.getTipoAccion().getId());
				accionDTO.setIdPantallaDestino(accion.getPantallaDestino().getId());
				accionDTO.setImagenB64(ImageConverterUtil.parseBytesToPNGB64String(accion.getImagen()));
				accionDTO.setNombreTipoAccion(accion.getTipoAccion().getNombre());
				accionDTO.setClaveModuloPantallaDestino(accion.getPantallaDestino().getModulo().getClave());
				accionDTO.setNumeroPantallaDestino(accion.getPantallaDestino().getNumero());
				accionDTO.setNombrePantallaDestino(accion.getPantallaDestino().getNombre());
				accionesDTO.add(accionDTO);
			}
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
		return accionesDTO;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarAccion(AccionDTO accionDTO, File archivo) {
		if (archivo != null) {
			if (rn040.isValidRN040(archivo)) {
				if (rn006.isValidRN006(accionDTO)) {
					Accion accion = new Accion();
					TipoAccion tipoAccion = genericoDAO.findById(TipoAccion.class, accionDTO.getIdTipoAccion());
					Pantalla pantalla = genericoDAO.findById(Pantalla.class, accionDTO.getIdPantalla());
					Pantalla pantallaDestino = genericoDAO.findById(Pantalla.class, accionDTO.getIdPantallaDestino());
					byte[] imagen = ImageConverterUtil.parseFileToBASE64ByteArray(archivo);
					accion.setNombre(accionDTO.getNombre());
					accion.setDescripcion(accionDTO.getDescripcion());
					accion.setTipoAccion(tipoAccion);
					accion.setPantalla(pantalla);
					accion.setPantallaDestino(pantallaDestino);
					accion.setImagen(imagen);
					genericoDAO.save(accion);
				} else {
					throw new TESSERACTValidacionException("EL nombre de la acción ya existe.", "MSG7",
							new String[] { "La", "Accion", accionDTO.getNombre() }, "model.nombre");
				}
			} else {
				throw new TESSERACTValidacionException("Archivo muy grande.", "MSG17", 
						new String[] { "2", "MB" }, null);
			}
		} else {
			throw new TESSERACTValidacionException("Seleccione una imagen.", "MSG30", null, "imagenPantalla");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modificarAccion(AccionDTO accionDTO, File archivo) {
		if (archivo != null) {
			if (rn040.isValidRN040(archivo)) {
				validarYEditar(accionDTO, archivo, true);
			} else {
				throw new TESSERACTValidacionException("Archivo muy grande.", "MSG17", 
						new String[] { "2", "MB" }, null);
			}
		} else {
			validarYEditar(accionDTO, archivo, false);
		}
	}
	
	private void validarYEditar(AccionDTO accionDTO, File archivo, Boolean cambiarImagen) {
		if (rn006.isValidRN006(accionDTO)) {
			Accion accion = genericoDAO.findById(Accion.class, accionDTO.getId());
			TipoAccion tipoAccion = genericoDAO.findById(TipoAccion.class, accionDTO.getIdTipoAccion());
			Pantalla pantallaDestino = genericoDAO.findById(Pantalla.class, accionDTO.getIdPantallaDestino());
			if (cambiarImagen) {
				byte[] imagen = ImageConverterUtil.parseFileToBASE64ByteArray(archivo);
				accion.setImagen(imagen);
			}
			accion.setNombre(accionDTO.getNombre());
			accion.setDescripcion(accionDTO.getDescripcion());
			accion.setTipoAccion(tipoAccion);
			accion.setPantallaDestino(pantallaDestino);
			genericoDAO.update(accion);
		} else {
			throw new TESSERACTValidacionException("EL nombre de la acción ya existe.", "MSG7",
					new String[] { "La", "Accion", accionDTO.getNombre() }, "model.nombre");
		}
	}
	
	public List<String> verificarReferencias(Accion model, Modulo modulo) {

		List<ReferenciaParametro> referenciasParametro;

		List<String> listReferenciasVista = new ArrayList<String>();
		Set<String> setReferenciasVista = new HashSet<String>(0);

		PostPrecondicion postPrecondicion = null; // Origen de la referencia
		Paso paso = null; // Origen de la referencia
		String casoUso = "";

		referenciasParametro = referenciaParametroDAO.consultarReferenciasParametro(model);

		for (ReferenciaParametro referencia : referenciasParametro) {
			String linea = "";
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();

			if (postPrecondicion != null && (modulo == null || postPrecondicion.getCasoUso().getModulo().getId() != modulo.getId())) {
				casoUso = postPrecondicion.getCasoUso().getClave()
						+ postPrecondicion.getCasoUso().getNumero() + " "
						+ postPrecondicion.getCasoUso().getNombre();
				if (postPrecondicion.isPrecondicion()) {
					linea = "Precondiciones del caso de uso " + casoUso;
				} else {
					linea = "Postcondiciones del caso de uso "
							+ postPrecondicion.getCasoUso().getClave()
							+ postPrecondicion.getCasoUso().getNumero() + " "
							+ postPrecondicion.getCasoUso().getNombre();
				}

			} else if (paso != null && (modulo == null || paso.getTrayectoria().getCasoUso().getModulo().getId() != modulo.getId())) {
				casoUso = paso.getTrayectoria().getCasoUso().getClave()
						+ paso.getTrayectoria().getCasoUso().getNumero() + " "
						+ paso.getTrayectoria().getCasoUso().getNombre();
				linea = "Paso "
						+ paso.getNumero()
						+ " de la trayectoria "
						+ ((paso.getTrayectoria().isAlternativa()) ? "alternativa "
								+ paso.getTrayectoria().getClave()
								: "principal") + " del caso de uso " + casoUso;
			}

			if (linea != "") {
				setReferenciasVista.add(linea);
			}
		}

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}
	
	public List<CasoUso> verificarCasosUsoReferencias(Accion model) {

		List<ReferenciaParametro> referenciasParametro;

		List<CasoUso> listReferenciasVista = new ArrayList<CasoUso>();
		Set<CasoUso> setReferenciasVista = new HashSet<CasoUso>(0);

		PostPrecondicion postPrecondicion = null; // Origen de la referencia
		Paso paso = null; // Origen de la referencia

		referenciasParametro = referenciaParametroDAO.consultarReferenciasParametro(model);

		for (ReferenciaParametro referencia : referenciasParametro) {
			String linea = "";
			postPrecondicion = referencia.getPostPrecondicion();
			paso = referencia.getPaso();

			if (postPrecondicion != null) {
				setReferenciasVista.add(postPrecondicion.getCasoUso());

			} else if (paso != null) {
				setReferenciasVista.add(paso.getTrayectoria().getCasoUso());
			}
		}

		listReferenciasVista.addAll(setReferenciasVista);

		return listReferenciasVista;
	}
	
}

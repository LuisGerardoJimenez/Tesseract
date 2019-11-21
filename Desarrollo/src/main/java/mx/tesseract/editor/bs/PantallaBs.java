package mx.tesseract.editor.bs;

import java.io.File;
import java.util.List;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN001;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.br.RN040;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.AccionDTO;
import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.dao.PantallaDAO;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.util.ImageConverterUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

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
	private RN018 rn018;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private ElementoBs elementoBs;
	
	@Autowired
	private ElementoDAO elementoDAO;

	public  List<Pantalla> consultarPantallasByModulo(Integer idProyecto, Integer idModulo) {
		return pantallaDAO.findAllByIdModulo(idProyecto, Clave.IU, idModulo);
	}
	
	public List<Pantalla> consultarPantallas(Integer idProyecto) {
		return elementoDAO.findAllByIdProyectoAndClave(Pantalla.class, idProyecto, Clave.IU);
	}
	
	public Pantalla consultarPantalla(Integer idPantalla) {
		Pantalla pantalla = genericoDAO.findById(Pantalla.class, idPantalla);
		if (pantalla == null) {
			throw new TESSERACTException("No se puede consultar la pantalla.", "MSG12");			
		}
		return pantalla;
	}
	
	public PantallaDTO consultarPantallaDTO(Integer idSel) {
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

	@Transactional(rollbackFor = Exception.class)
	public void eliminarPantalla(PantallaDTO model, Integer idProyecto) throws TESSERACTException{
		if (rn018.isValidRN018(model)) {
			Pantalla pantalla = genericoDAO.findById(Pantalla.class, model.getId());
			for(Accion accion : pantalla.getAcciones()) {
				AccionDTO accionDTO = new AccionDTO();
				accionDTO.setId(accion.getId());
				if(!rn018.isValidRN018(accionDTO, idProyecto)) throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.", "MSG13");
			}
			genericoDAO.delete(pantalla);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

}

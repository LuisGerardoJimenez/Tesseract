package mx.tesseract.editor.bs;

import java.util.List;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.EntidadDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

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
		return elementoDAO.findAllByIdProyectoAndClave(Entidad.class, idProyecto, Clave.ENT);
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
	public void eliminarEntidad(EntidadDTO model) throws TESSERACTException{
		if (rn018.isValidRN018(model)) {
			Entidad entidad = genericoDAO.findById(Entidad.class, model.getId());
			genericoDAO.delete(entidad);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

}

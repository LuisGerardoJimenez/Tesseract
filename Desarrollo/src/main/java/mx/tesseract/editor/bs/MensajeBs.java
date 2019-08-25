package mx.tesseract.editor.bs;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mensajeBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class MensajeBs {

	@Autowired
	private RN006 rn006;
	
	@Autowired
	private RN018 rn018;

	@Autowired
	private ElementoDAO elementoDAO;

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ElementoBs elementoBs;

	public List<Mensaje> consultarMensajeProyecto(Integer idProyecto) {
		List<Mensaje> listMensaje = null;
		listMensaje = elementoDAO.findAllByIdProyectoAndClave(idProyecto, Clave.MSG, Mensaje.class);
		System.out.println("TAMAÑO DE: "+listMensaje.size());
		return listMensaje;
	}

	public MensajeDTO consultarMensajeById(Integer id) {
		Mensaje mensaje = genericoDAO.findById(Mensaje.class, id);
		MensajeDTO mensajeDTO = new MensajeDTO();
		if (mensaje != null) {
			mensajeDTO.setId(mensaje.getId());
			mensajeDTO.setIdProyecto(mensaje.getProyecto().getId());
			//mensajeDTO.setParametrizado(mensaje.getParametrizado());
			mensajeDTO.setNombre(mensaje.getNombre());
			//mensajeDTO.setRedaccion(mensaje.getRedaccion());
			
		} else {
			throw new TESSERACTException("No se puede consultar el mensaje.", "MSG12");
		}
		return mensajeDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarMensaje(MensajeDTO mensajeDTO) {
		//if (rn006.isValidRN006(mensajeDTO)) {
			Mensaje mensaje = new Mensaje();
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, mensajeDTO.getIdProyecto());
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.GLS);
			mensaje.setClave(Clave.MSG.toString());
			mensaje.setNumero(numero);
			mensaje.setNombre(mensajeDTO.getNombre());
			mensaje.setDescripcion(mensajeDTO.getDescripcion());
			mensaje.setProyecto(proyecto);
			mensaje.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			mensaje.setRedaccion(mensajeDTO.getRedaccion());
			mensaje.setParametrizado(1);
			genericoDAO.save(mensaje);
		/*} else {
			throw new TESSERACTValidacionException("EL nombre del término ya existe.", "MSG7",
					new String[] { "El", "Módulo", terminoGlosarioDTO.getNombre() }, "model.nombre");
		}*/
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarTerminoGlosario(TerminoGlosarioDTO terminoGlosarioDTO) {
		if (rn006.isValidRN006(terminoGlosarioDTO)) {
			TerminoGlosario terminoGlosario = genericoDAO.findById(TerminoGlosario.class, terminoGlosarioDTO.getId());
			terminoGlosario.setNombre(terminoGlosarioDTO.getNombre());
			terminoGlosario.setDescripcion(terminoGlosarioDTO.getDescripcion());
			genericoDAO.update(terminoGlosario);
		} else {
			throw new TESSERACTValidacionException("EL nombre del término ya existe.", "MSG7",
					new String[] { "El", "Módulo", terminoGlosarioDTO.getNombre() }, "model.nombre");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarMensaje(MensajeDTO mensajeDTO) {
		if (rn018.isValidRN018(mensajeDTO)) {
			Mensaje mensaje = genericoDAO.findById(Mensaje.class, mensajeDTO.getId());
			genericoDAO.delete(mensaje);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

}

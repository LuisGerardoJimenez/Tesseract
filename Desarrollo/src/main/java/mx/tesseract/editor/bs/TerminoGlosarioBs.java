package mx.tesseract.editor.bs;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN023;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("terminoGlosarioBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TerminoGlosarioBs {

	@Autowired
	private RN006 rn006;

	@Autowired
	private RN023 rn023;

	@Autowired
	private ElementoDAO elementoDAO;

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ElementoBs elementoBs;

	public List<TerminoGlosario> consultarGlosarioProyecto(Integer idProyecto) {
		List<TerminoGlosario> listGlosario = elementoDAO.findAllByIdProyectoAndClave(idProyecto, Clave.GLS);
		return listGlosario;
	}
	
	public TerminoGlosarioDTO consultarTerminoGlosarioById(Integer id) {
		TerminoGlosario terminoGlosario = genericoDAO.findById(TerminoGlosario.class, id);
		TerminoGlosarioDTO terminoGlosarioDTO = new TerminoGlosarioDTO();
		if (terminoGlosario != null) {
			terminoGlosarioDTO.setId(terminoGlosario.getId());
			terminoGlosarioDTO.setNombre(terminoGlosario.getNombre());
			terminoGlosarioDTO.setDescripcion(terminoGlosario.getDescripcion());
			terminoGlosarioDTO.setIdProyecto(terminoGlosario.getProyecto().getId());
		} else {
			throw new TESSERACTException("No se puede consultar el termino.", "MSG12");
		}
		return terminoGlosarioDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarTerminoGlosario(TerminoGlosarioDTO terminoGlosarioDTO) {
		if (rn006.isValidRN006(terminoGlosarioDTO)) {
			TerminoGlosario terminoGlosario = new TerminoGlosario();
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, terminoGlosarioDTO.getIdProyecto());
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.GLS);
			terminoGlosario.setClave(Clave.GLS.toString());
			terminoGlosario.setNumero(numero);
			terminoGlosario.setNombre(terminoGlosarioDTO.getNombre());
			terminoGlosario.setDescripcion(terminoGlosarioDTO.getDescripcion());
			terminoGlosario.setProyecto(proyecto);
			terminoGlosario.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			genericoDAO.save(terminoGlosario);
		} else {
			throw new TESSERACTValidacionException("EL nombre del término ya existe.", "MSG7",
					new String[] { "El", "Módulo", terminoGlosarioDTO.getNombre() }, "model.nombre");
		}
	}

}

package mx.tesseract.editor.bs;

import java.io.File;
import java.util.List;

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
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.TipoAccion;
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
	
	public List<Accion> consultarAccionesByPantalla(Integer idPantalla) {
		List<Accion> acciones = accionDAO.findAllByPantalla(idPantalla);
		return acciones;
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
					accion.setNombre(accionDTO.getNombre());
					accion.setDescripcion(accionDTO.getDescripcion());
					accion.setTipoAccion(tipoAccion);
					accion.setPantalla(pantalla);
					accion.setPantallaDestino(pantallaDestino);
//					TODO: Terminar de urlDestino y metodo
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
}

package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.br.RN006;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.PasoDTO;
import mx.tesseract.editor.dao.VerboDAO;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.editor.entidad.Verbo;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

@Service("pasoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class PasoBs {

	@Autowired
	private TokenBs tokenBs;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private VerboDAO verboDAO;
	
	@Autowired
	private RN006 rn006;
	
	public List<PasoDTO> obtenerPasos(Trayectoria trayectoria, Integer idModulo) {
		List<PasoDTO> pasosDTO = new ArrayList<>();
		PasoDTO pasoDTO;
		for(Paso paso : trayectoria.getPasos()) {
			paso.setRedaccion(tokenBs.decodificarCadenasToken(paso
					.getRedaccion()));
			pasoDTO = new PasoDTO(paso.getId(), paso.getNumero(),paso.isRealizaActor(),paso.getRedaccion(),trayectoria.getId(),paso.getVerbo().getId(), paso.getVerbo().getNombre(), paso.getOtroVerbo());
			pasosDTO.add(pasoDTO);
		}
		return pasosDTO;
	}

	public PasoDTO consultarPasoById(Integer idSel) {
		PasoDTO pasoDTO = new PasoDTO();
		Paso paso = genericoDAO.findById(Paso.class, idSel);
		pasoDTO.setId(paso.getId());
		pasoDTO.setIdTrayectoria(paso.getTrayectoria().getId());
		pasoDTO.setIdVerbo(paso.getVerbo().getId());
		pasoDTO.setNumero(paso.getNumero());
		pasoDTO.setOtroVerbo(paso.getOtroVerbo());
		pasoDTO.setRealizaActor(paso.isRealizaActor());
		pasoDTO.setRedaccion(paso.getRedaccion());
		pasoDTO.setVerbo(paso.getVerbo().getNombre());
		return pasoDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarPaso(PasoDTO model) {
		if (rn006.isValidRN006(model)) {
			Trayectoria trayectoria = genericoDAO.findById(Trayectoria.class, model.getIdTrayectoria());
			Verbo verbo = verboDAO.findByNombre(model.getVerbo());
			Paso entidad = new Paso();
			entidad.setNumero(trayectoria.getPasos().size() + Constantes.NUMERO_UNO);
			entidad.setOtroVerbo(model.getOtroVerbo());
			entidad.setRealizaActor(model.getRealizaActor());
			entidad.setRedaccion(model.getRedaccion());
			entidad.setTrayectoria(trayectoria);
			entidad.setVerbo(verbo);
			genericoDAO.save(entidad);
		} else {
			throw new TESSERACTValidacionException("La redacción del paso ya existe.", "MSG7",
					new String[] { "El", "paso", model.getRedaccion() }, "model.redaccion");
		}
	}
	
	public void preAlmacenarObjetosToken(PasoDTO model, CasoUso casoUso, Integer idModulo) {
		model.setRedaccion(tokenBs.codificarCadenaToken(
			model.getRedaccion(), casoUso.getProyecto(), idModulo));
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarPaso(PasoDTO pasoDTO) {
		Trayectoria trayectoria = genericoDAO.findById(Trayectoria.class, pasoDTO.getIdTrayectoria());
		Paso paso = genericoDAO.findById(Paso.class, pasoDTO.getId());
		Integer numeroMaximo = trayectoria.getPasos().size();
		Integer numeroActual = paso.getNumero() + Constantes.NUMERO_UNO;
		while(numeroActual <= numeroMaximo) {
			for(Paso pasoItem : trayectoria.getPasos()) {
				if(pasoItem.getNumero().equals(numeroActual)) {
					pasoItem.setNumero(numeroActual - Constantes.NUMERO_UNO);
					genericoDAO.update(pasoItem);
				}
			}
			numeroActual++;
		}
		genericoDAO.delete(paso);
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarPaso(PasoDTO pasoDTO) throws TESSERACTValidacionException {
		if (rn006.isValidRN006(pasoDTO)) {
			Verbo verbo = verboDAO.findByNombre(pasoDTO.getVerbo());
			Paso paso = genericoDAO.findById(Paso.class, pasoDTO.getId());
			paso.setOtroVerbo(pasoDTO.getOtroVerbo());
			paso.setRealizaActor(pasoDTO.getRealizaActor());
			paso.setRedaccion(pasoDTO.getRedaccion());
			paso.setVerbo(verbo);
			genericoDAO.update(paso);
		} else {
			throw new TESSERACTValidacionException("La redacción del paso ya existe.", "MSG7",
					new String[] { "El", "paso", pasoDTO.getRedaccion() }, "model.redaccion");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void subirPaso(PasoDTO model) throws TESSERACTException{
		Trayectoria trayectoria = genericoDAO.findById(Trayectoria.class, model.getIdTrayectoria());
		if(model.getNumero() < trayectoria.getPasos().size() && trayectoria.getPasos().size() > 1) {
			Paso actual = genericoDAO.findById(Paso.class, model.getId());
			Paso anterior = null;
			for(Paso paso : trayectoria.getPasos()) {
				if(paso.getNumero().equals(actual.getNumero() + Constantes.NUMERO_UNO)) {
					anterior = paso;
				}
			}
			Integer numero;
			numero = actual.getNumero();
			actual.setNumero(anterior.getNumero());
			anterior.setNumero(numero);
			genericoDAO.update(actual);
			genericoDAO.update(anterior);
		} else {
			throw new TESSERACTException("No puede realizar esta acción.", "MSG7");
		}
			
	}

	@Transactional(rollbackFor = Exception.class)
	public void bajarPaso(PasoDTO model) {
		Trayectoria trayectoria = genericoDAO.findById(Trayectoria.class, model.getIdTrayectoria());
		if(model.getNumero() > Constantes.NUMERO_UNO && trayectoria.getPasos().size() > 1) {
			Paso actual = genericoDAO.findById(Paso.class, model.getId());
			Paso siguiente = null;
			for(Paso paso : trayectoria.getPasos()) {
				if(paso.getNumero().equals(actual.getNumero() - Constantes.NUMERO_UNO)) {
					siguiente = paso;
				}
			}
			Integer numero;
			numero = actual.getNumero();
			actual.setNumero(siguiente.getNumero());
			siguiente.setNumero(numero);
			genericoDAO.update(actual);
			genericoDAO.update(siguiente);
		}else {
			throw new TESSERACTException("No puede realizar esta acción.", "MSG7");
		}
	}
	
}

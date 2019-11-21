package mx.tesseract.br;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dto.AccionDTO;
import mx.tesseract.dto.ActorDTO;
import mx.tesseract.dto.AtributoDTO;
import mx.tesseract.dto.CasoUsoDTO;
import mx.tesseract.dto.EntidadDTO;
import mx.tesseract.dto.ExtensionDTO;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.dto.ReglaNegocioDTO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.dto.TrayectoriaDTO;
import mx.tesseract.editor.dao.CasoUsoDAO;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.Constantes;

@Service("rN018")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN018 {	
	
	@Autowired
	private CasoUsoDAO casoUsoDAO;
	
	public Boolean isValidRN018(TrayectoriaDTO entidad, Integer idProyecto) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(idProyecto);
		for(CasoUso casoUso : casosUso) {
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.RN+"·"+entidad.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;	
		}
		
		return valido;
	}
	
	public Boolean isValidRN018(MensajeDTO entidad) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(entidad.getIdProyecto());
		for(CasoUso casoUso : casosUso) {
			if(casoUso.getRedaccionEntradas().contains(Clave.MSG+"·"+entidad.getId())) {
				valido = false;
				break;
			}else if(casoUso.getRedaccionSalidas().contains(Clave.MSG+"·"+entidad.getId())) {
				valido = false;
				break;
			}
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.MSG+"·"+entidad.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;
		}
		return valido;
	}
	
	public Boolean isValidRN018(TerminoGlosarioDTO entidad) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(entidad.getIdProyecto());
		for(CasoUso casoUso : casosUso) {
			if(casoUso.getRedaccionEntradas().contains(Clave.GLS+"·"+entidad.getId())) {
				valido = false;
				break;
			}else if(casoUso.getRedaccionSalidas().contains(Clave.GLS+"·"+entidad.getId())) {
				valido = false;
				break;
			}
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.GLS+"·"+entidad.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;
		}
		return valido;
	}
	
	public Boolean isValidRN018(AtributoDTO entidad) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(entidad.getProyectoId());
		for(CasoUso casoUso : casosUso) {
			if(casoUso.getRedaccionEntradas().contains(Constantes.TIPO_REFERENCIA_ATRIBUTO+"·"+entidad.getId())) {
				valido = false;
				break;
			}else if(casoUso.getRedaccionSalidas().contains(Constantes.TIPO_REFERENCIA_ATRIBUTO+"·"+entidad.getId())) {
				valido = false;
				break;
			}
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Constantes.TIPO_REFERENCIA_ATRIBUTO+"·"+entidad.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;
		}
		return valido;
	}
	
	public Boolean isValidRN018(EntidadDTO entidadDTO) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(entidadDTO.getIdProyecto());
		for(CasoUso casoUso : casosUso) {
			if(casoUso.getRedaccionEntradas().contains(Clave.ENT+"·"+entidadDTO.getId())) {
				valido = false;
				break;
			}else if(casoUso.getRedaccionSalidas().contains(Clave.ENT+"·"+entidadDTO.getId())) {
				valido = false;
				break;
			}
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.ENT+"·"+entidadDTO.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;
		}
		return valido;
	}

	public boolean isValidRN018(ReglaNegocioDTO modelDTO) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(modelDTO.getIdProyecto());
		for(CasoUso casoUso : casosUso) {
			if(casoUso.getRedaccionReglasNegocio().contains(Clave.RN+"·"+modelDTO.getId())) {
				valido = false;
				break;
			}
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.RN+"·"+modelDTO.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;
		}
		return valido;
	}

	public boolean isValidRN018(ActorDTO actorDTO) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(actorDTO.getIdProyecto());
		for(CasoUso casoUso : casosUso) {
			if(casoUso.getRedaccionActores().contains(Clave.ACT+"·"+actorDTO.getId())) {
				valido = false;
				break;
			}
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.ACT+"·"+actorDTO.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;
		}
		return valido;
	}

	public boolean isValidRN018(PantallaDTO model) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(model.getIdProyecto());
		for(CasoUso casoUso : casosUso) {
			if(casoUso.getRedaccionSalidas().contains(Clave.IU+"·"+model.getId())) {
				valido = false;
				break;
			}
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.IU+"·"+model.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;
		}
		return valido;
	}
	
	public boolean isValidRN018(AccionDTO model, Integer idProyecto) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(idProyecto);
		for(CasoUso casoUso : casosUso) {
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.CU+"·"+model.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;	
		}
		return valido;
	}
	
	public boolean isValidRN018(CasoUsoDTO model) {
		Boolean valido = true;
		List<CasoUso> casosUso = casoUsoDAO.findAllByProyecto(model.getIdProyecto());
		for(CasoUso casoUso : casosUso) {
			for(Trayectoria trayectoria : casoUso.getTrayectorias()) {
				for(Paso paso : trayectoria.getPasos()) {
					if(paso.getRedaccion().contains(Clave.CU+"·"+model.getId())) {
						valido = false;
						break;
					}
				}
				if(!valido)
					break;
			}
			if(!valido)
				break;	
		}
		return valido;
	}

	public boolean isValidRN018(ExtensionDTO model) {
		Boolean valido = true;
		return valido;
	}

}

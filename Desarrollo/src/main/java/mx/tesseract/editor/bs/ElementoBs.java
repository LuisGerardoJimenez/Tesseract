package mx.tesseract.editor.bs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.EstadoElemento;
import mx.tesseract.enums.AnalisisEnum.CU_CasosUso;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ElementoInterface;
import mx.tesseract.util.TESSERACTException;

@Service("elementoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ElementoBs {
	
	private static final int ID_EDICION = 1;
	private static final int ID_REVISION = 2;
	private static final int ID_PENDIENTECORRECCION = 3;
	private static final int ID_PORLIBERAR = 4;
	private static final int ID_LIBERADO = 5;
	private static final int ID_PRECONFIGURADO = 6;
	private static final int ID_CONFIGURADO = 7;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private ElementoBs elementoBs;
	
	@Autowired
	private ElementoDAO elementoDAO;
	
	public EstadoElemento consultarEstadoElemento(Estado estado) {
		Integer idEstado;
		switch(estado) {
		case EDICION:
			idEstado = Constantes.ESTADO_ELEMENTO_EDICION;
			break;
		case LIBERADO:
			idEstado = Constantes.ESTADO_ELEMENTO_LIBERADO;
			break;
		case PENDIENTECORRECCION:
			idEstado = Constantes.ESTADO_ELEMENTO_PENDIENTECORRECCION;
			break;
		case PORLIBERAR:
			idEstado = Constantes.ESTADO_ELEMENTO_PORLIBERAR;
			break;
		case REVISION:
			idEstado = Constantes.ESTADO_ELEMENTO_REVISION;
			break;
		case PRECONFIGURADO:
			idEstado = Constantes.ESTADO_ELEMENTO_PRECONFIGURADO;
			break;
		default:
			idEstado = Constantes.ESTADO_ELEMENTO_CONFIGURADO;
			break;
		}
		EstadoElemento estadoElemento = genericoDAO.findById(EstadoElemento.class, idEstado);
		if(estadoElemento == null) {
			throw new TESSERACTException("No se puede consultar el estado del elemento", "MSG12");
		}
		return estadoElemento;
	}
	
//	public void verificarEstado(Elemento elemento,
//			CU_Mensajes mensajeAnalisis) {
//		switch(mensajeAnalisis) {
//		case MODIFICARMENSAJE9_2:
//			if (elemento.getEstadoElemento().getId() != Constantes.ESTADO_ELEMENTO_EDICION) {
//				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
//			}
//			break;
//		case ELIMINARMENSAJE9_3:
//			if (elemento.getEstadoElemento().getId() != Constantes.ESTADO_ELEMENTO_EDICION) {
//				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
	
	public void verificarEstado(CasoUso elemento, CU_CasosUso casoUsoAnalisis) throws TESSERACTException {
		switch(casoUsoAnalisis) {
		case MODIFICARCASOUSO5_2:
			if (elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.PENDIENTECORRECCION)) {
					throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
			}
			
		case ELIMINARCASOUSO5_3:
			if (elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.PENDIENTECORRECCION)) {
				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
		}			break;
		case REVISARCASOUSO5_5:
			if (elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.REVISION)) {
				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
		}
			break;
		case TERMINARCASOUSO5_6:
			if (elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.PENDIENTECORRECCION)) {
				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
		}
			break;
		case LIBERARCASOUSO4_3:
			if (elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.PORLIBERAR) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.LIBERADO)
					&& elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.CONFIGURADO) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.PRECONFIGURADO)) {
				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
		}
			break;
		case CONFIGURARPRUEBA5_7:
			if (elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.LIBERADO) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.PRECONFIGURADO) && elemento.getEstadoElemento().getId() != elementoBs.getIdEstado(Estado.CONFIGURADO)) {
				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
			}
			break;
		default:
			break;
		
		}
	}
	
	public int getIdEstado(Estado estado) {
		switch(estado) {
		case EDICION:
			return ID_EDICION;
		case LIBERADO:
			return ID_LIBERADO;
		case PENDIENTECORRECCION:
			return ID_PENDIENTECORRECCION;
		case PORLIBERAR:
			return ID_PORLIBERAR;
		case REVISION:
			return ID_REVISION;
		case PRECONFIGURADO:
			return ID_PRECONFIGURADO;
		case CONFIGURADO:
			return ID_CONFIGURADO;
		default:
			return 0;
		}
	}
	
	public <T extends ElementoInterface> T findAllByIdProyectoAndIdAndNombreAndClave(Class<T> clase, Integer idProyecto, Integer id, String nombre, Clave clave) {
		return elementoDAO.findAllByIdProyectoAndIdAndNombreAndClave(clase, idProyecto, id, nombre, clave);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modificarEstadoElemento(Elemento elemento, Estado estado) throws Exception {
		elemento.setEstadoElemento(consultarEstadoElemento(estado));
		genericoDAO.update(elemento);
	}
}


package mx.tesseract.editor.bs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.EstadoElemento;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;

@Service("elementoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ElementoBs {
	
	@Autowired
	private GenericoDAO genericoDAO;
	
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
	
//	public static void verificarEstado(Elemento elemento, CU_CasosUso casoUsoAnalisis) {
//		switch(casoUsoAnalisis) {
//		case MODIFICARCASOUSO5_2:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.PENDIENTECORRECCION)) {
//					throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
//			}
//			
//		case ELIMINARCASOUSO5_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.PENDIENTECORRECCION)) {
//				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
//		}			break;
//		case REVISARCASOUSO5_5:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.REVISION)) {
//				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
//		}
//			break;
//		case TERMINARCASOUSO5_6:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.PENDIENTECORRECCION)) {
//				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
//		}
//			break;
//		case LIBERARCASOUSO4_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.PORLIBERAR) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.LIBERADO)
//					&& elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.CONFIGURADO) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.PRECONFIGURADO)) {
//				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
//		}
//			break;
//		case CONFIGURARPRUEBA5_7:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.LIBERADO) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.PRECONFIGURADO) && elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.CONFIGURADO)) {
//				throw new TESSERACTException("El estado del caso de uso es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		
//		}
//	}
//
//	public static void verificarEstado(Elemento elemento,
//			CU_ReglasNegocio reglaNegocioAnalisis) {
//		switch(reglaNegocioAnalisis) {
//		case MODIFICARREGLANEGOCIO8_2:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
//			}
//			break;
//		case ELIMINARREGLANEGOCIO8_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
//	
//	public static void verificarEstado(Elemento elemento,
//			CU_Mensajes mensajeAnalisis) {
//		switch(mensajeAnalisis) {
//		case MODIFICARMENSAJE9_2:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
//			}
//			break;
//		case ELIMINARMENSAJE9_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
//	
//	public static void verificarEstado(Elemento elemento,
//			CU_Pantallas pantallaAnalisis) {
//		switch(pantallaAnalisis) {
//		case MODIFICARPANTALLA6_2:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado de la pantalla es inválido.", "MSG13");
//			}
//			break;
//		case ELIMINARPANTALLA6_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado de la pantalla es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
//
//	public static void verificarEstado(Actor elemento, CU_Actores actorAnalisis) {
//		switch(actorAnalisis) {
//		case MODIFICARACTOR7_2:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado del actor es inválido.", "MSG13");
//			}
//			break;
//		case ELIMINARACTOR7_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado del actor es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
//	
//	public static void verificarEstado(TerminoGlosario elemento, CU_Glosario glosarioAnalisis) {
//		switch(glosarioAnalisis) {
//		case MODIFICARTERMINO10_2:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado del término es inválido.", "MSG13");
//			}
//			break;
//		case ELIMINARTERMINO10_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado del término es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
//	
//	public static void verificarEstado(Entidad elemento, CU_Entidades entidadAnalisis) {
//		switch(entidadAnalisis) {
//		case MODIFICARENTIDAD11_2:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado del término es inválido.", "MSG13");
//			}
//			break;
//		case ELIMINARENTIDAD11_3:
//			if (elemento.getEstadoElemento().getId() != ElementoBs.getIdEstado(Estado.EDICION)) {
//				throw new TESSERACTException("El estado del término es inválido.", "MSG13");
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
//	
//	public static void modificarEstadoElemento(Elemento elemento, Estado estado) throws Exception {
//		elemento.setEstadoElemento(ElementoBs
//				.consultarEstadoElemento(estado));
//		new ElementoDAO().modificarElemento(elemento);
//	}
}


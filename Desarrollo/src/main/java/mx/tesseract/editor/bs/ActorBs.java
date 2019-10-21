package mx.tesseract.editor.bs;

import java.util.List;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.ActorDTO;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.editor.dao.ElementoDAO;
//import mx.tesseract.bs.AnalisisEnum.CU_Actores;
//import mx.tesseract.bs.ReferenciaEnum.TipoCatalogo;
//import mx.tesseract.editor.bs.ElementoBs.Estado;
//import mx.tesseract.editor.dao.ActorDAO;
//import mx.tesseract.editor.dao.ActualizacionDAO;
//import mx.tesseract.editor.dao.CardinalidadDAO;
//import mx.tesseract.editor.dao.CasoUsoActorDAO;
//import mx.tesseract.editor.dao.ReferenciaParametroDAO;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Cardinalidad;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
//import mx.tesseract.editor.entidad.CasoUso;
//import mx.tesseract.editor.entidad.CasoUsoActor;
//import mx.tesseract.editor.entidad.Paso;
//import mx.tesseract.editor.entidad.PostPrecondicion;
//import mx.tesseract.editor.entidad.ReferenciaParametro;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("actorBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ActorBs {
	
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
	
	public List<Actor> consultarActoresProyecto(Integer idProyecto) {
		List<Actor> listActores = elementoDAO.findAllByIdProyectoAndClave(Actor.class, idProyecto, Clave.ACT);
		return listActores;
	}
	
	public ActorDTO consultarActorById(Integer id) {
		Actor actor = genericoDAO.findById(Actor.class, id);
		ActorDTO actorDTO = new ActorDTO();
		if (actor != null) {
			actorDTO.setId(actor.getId());
			actorDTO.setNombre(actor.getNombre());
			actorDTO.setDescripcion(actor.getDescripcion());
			actorDTO.setCardinalidadId(actor.getCardinalidad().getId());
			actorDTO.setCardinalidadNombre(actor.getCardinalidad().getNombre());
			actorDTO.setOtraCardinalidad(actor.getOtraCardinalidad());
			actorDTO.setIdProyecto(actor.getProyecto().getId());
		} else {
			throw new TESSERACTException("No se puede consultar el actor.", "MSG12");
		}
		return actorDTO;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarActor(ActorDTO actorDTO) {
		if (rn006.isValidRN006(actorDTO)) {
			Actor actor = new Actor();
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, actorDTO.getIdProyecto());
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.ACT);
			actor.setClave(Clave.ACT.toString());
			actor.setNumero(numero);
			actor.setNombre(actorDTO.getNombre());
			actor.setDescripcion(actorDTO.getDescripcion());
			actor.setProyecto(proyecto);
			actor.setCardinalidad(genericoDAO.findById(Cardinalidad.class, actorDTO.getCardinalidadId()));
			actor.setOtraCardinalidad(actorDTO.getOtraCardinalidad());
			actor.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			genericoDAO.save(actor);
		} else {
			throw new TESSERACTValidacionException("EL nombre del actor ya existe.", "MSG7",
					new String[] { "El", "Actor", actorDTO.getNombre() }, "model.nombre");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarActor(ActorDTO actorDTO) {
		if (rn006.isValidRN006(actorDTO)) {
			Actor actor = genericoDAO.findById(Actor.class, actorDTO.getId());
			actor.setNombre(actorDTO.getNombre());
			actor.setDescripcion(actorDTO.getDescripcion());
			actor.setCardinalidad(genericoDAO.findById(Cardinalidad.class, actorDTO.getCardinalidadId()));
			actor.setOtraCardinalidad(actorDTO.getOtraCardinalidad());
			genericoDAO.update(actor);
		} else {
			throw new TESSERACTValidacionException("EL nombre del actor ya existe.", "MSG7",
					new String[] { "El", "Actor", actorDTO.getNombre() }, "model.nombre");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarActor(ActorDTO actorDTO) {
		if (rn018.isValidRN018(actorDTO)) {
			Actor actor = genericoDAO.findById(Actor.class, actorDTO.getId());
			genericoDAO.delete(actor);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

}

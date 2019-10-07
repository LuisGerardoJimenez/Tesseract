package mx.tesseract.editor.bs;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.dao.MensajeParametroDAO;
import mx.tesseract.editor.dao.ParametroDAO;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.MensajeParametro;
import mx.tesseract.editor.entidad.Parametro;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import java.util.ArrayList;
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
	
	@Autowired
	private TokenBs tokenBs;
	
	@Autowired
	private ParametroDAO parametroDAO;
	
	@Autowired
	private MensajeParametroDAO mensajeParametroDAO;

	public List<Mensaje> consultarMensajeProyecto(Integer idProyecto) {
		List<Mensaje> listMensaje = null;
		listMensaje = elementoDAO.findAllByIdProyectoAndClave(Mensaje.class, idProyecto, Clave.MSG);
		return listMensaje;
	}

	public MensajeDTO consultarMensajeById(Integer id) {
		Mensaje mensaje = genericoDAO.findById(Mensaje.class, id);
		MensajeDTO mensajeDTO = new MensajeDTO();
		if (mensaje != null) {
			
			mensajeDTO.setClave(mensaje.getClave());
			mensajeDTO.setIdProyecto(mensaje.getProyecto().getId());
			mensajeDTO.setNumero(mensaje.getNumero());
			mensajeDTO.setNombre(mensaje.getNombre());
			mensajeDTO.setId(mensaje.getId());
			mensajeDTO.setRedaccion(mensaje.getRedaccion());
			mensajeDTO.setParametrizado(mensaje.getParametrizado());
			mensajeDTO.setDescripcion(mensaje.getDescripcion());
			mensajeDTO.setEstadoElemento(mensaje.getEstadoElemento());
			mensajeDTO.setParametros(mensaje.getParametros());
			
		} else {
			throw new TESSERACTException("No se puede consultar el mensaje.", "MSG12");
		}
		return mensajeDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	public void registrarMensaje(MensajeDTO mensajeDTO) {
		if (rn006.isValidRN006(mensajeDTO)) {
			Mensaje mensaje = new Mensaje();
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, mensajeDTO.getIdProyecto());
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.MSG);
			mensaje.setClave(Clave.MSG.toString());
			mensaje.setNumero(numero);
			mensaje.setNombre(mensajeDTO.getNombre());
			mensaje.setDescripcion(mensajeDTO.getDescripcion());
			mensaje.setProyecto(proyecto);
			mensaje.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			mensaje.setRedaccion(mensajeDTO.getRedaccion());
			mensaje.setParametrizado(Boolean.TRUE);
			genericoDAO.save(mensaje);
			for(MensajeParametro mensajeParametro : mensajeDTO.getParametros()) {
				if(mensajeParametro.getParametro().getId()== null) {
					mensajeParametro.getParametro().setProyecto(proyecto);
					mensajeParametro.setParametro(genericoDAO.save(mensajeParametro.getParametro()));
				}
				mensajeParametro.setMensaje(mensaje);
				genericoDAO.save(mensajeParametro);
			}
			
		} else {
			throw new TESSERACTValidacionException("EL nombre del mensaje ya existe.", "MSG7",
					new String[] { "El", "Mensaje", mensajeDTO.getNombre() }, "model.nombre");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarMensaje(MensajeDTO mensajeDTO) {
		Mensaje mensaje = genericoDAO.findById(Mensaje.class, mensajeDTO.getId());
		Proyecto proyecto = genericoDAO.findById(Proyecto.class, mensajeDTO.getIdProyecto());
		mensaje.setClave(Clave.MSG.toString());
		mensaje.setNombre(mensajeDTO.getNombre());
		mensaje.setDescripcion(mensajeDTO.getDescripcion());
		mensaje.setProyecto(proyecto);
		mensaje.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
		ArrayList<String> listParametrosDTO = new ArrayList<String>(); 
		for(Parametro parametro : obtenerParametros(mensajeDTO.getRedaccion(), proyecto.getId())) {
			listParametrosDTO.add(parametro.getNombre());
		}
		ArrayList<String> listParametros = new ArrayList<String>(); 
		for(Parametro parametro : obtenerParametros(mensaje.getRedaccion(), proyecto.getId())) {
			listParametros.add(parametro.getNombre());
		}
		mensaje.setRedaccion(mensajeDTO.getRedaccion());
		mensaje.setParametrizado(mensajeDTO.getParametrizado());
		
		for(MensajeParametro mensajeParametro : mensajeDTO.getParametros()) {
			for(MensajeParametro mensajeParametroItem : mensaje.getParametros()) {
				
				if(mensajeParametro.getParametro().getNombre().equals(mensajeParametroItem.getParametro().getNombre())) {
					mensajeParametro.getParametro().setNombre(mensajeParametroItem.getParametro().getNombre());
					mensajeParametro.getParametro().setDescripcion(mensajeParametroItem.getParametro().getDescripcion());
				}
				
			}
			if(mensajeParametro.getParametro().getId() == null) {
				mensajeParametro.getParametro().setProyecto(proyecto);
				genericoDAO.save(mensajeParametro.getParametro());
				mensajeParametro.setMensaje(mensaje);
				genericoDAO.save(mensajeParametro);
			}
			if(!listParametros.contains(mensajeParametro.getParametro().getNombre()) && listParametrosDTO.contains(mensajeParametro.getParametro().getNombre())) {
				mensajeParametro.setMensaje(mensaje);
				genericoDAO.save(mensajeParametro);
			}
			
			
		}
		genericoDAO.update(mensaje);
		for(String parametroItem : listParametros) {
			if(!listParametrosDTO.contains(parametroItem)) {
				Parametro parametro = parametroDAO.consultarParametro(parametroItem, proyecto.getId());
				MensajeParametro  mensajeParametro = mensajeParametroDAO.consultarMensajeParametro(mensaje.getId(), parametro.getId());
				genericoDAO.delete(mensajeParametro);
				if(mensajeParametroDAO.consultarMensajeParametro(parametro.getId()) == null) {
					genericoDAO.delete(parametro);
				}
			}
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
	
	/* FUNCIONES ADICIONALES */
	public boolean esParametrizado(String redaccion) {
		ArrayList<String> tokens = tokenBs.procesarTokenIpunt(redaccion);
		if(tokens.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<Parametro> obtenerParametros(String redaccion, int idProyecto) {
		//Se convierte la lista de parametros en json para enviarlos a la vista
		ArrayList<String> tokens = tokenBs.procesarTokenIpunt(redaccion);
		ArrayList<Parametro> listParametros = new ArrayList<Parametro>();
		Parametro parametroAux = null;
		if(listParametros.size() > 10) {
			throw new TESSERACTValidacionException("El usuario no ingresó la descripcion de algun parametros del mensaje.", "MSG6", new String[]{"10", "parámetros"}, 
					"model.parametros");
		}
		ArrayList<String> segmentos;
		for(String token : tokens) {
			segmentos = tokenBs.segmentarToken(token);
			//Se hace la consulta con base en el nombre
			Parametro parametro = consultarParametro(segmentos.get(1), idProyecto);
			if(parametro == null) {
				//Si el parámetro existe en la bd
				parametro = new Parametro(segmentos.get(1),"");
			}
			if (!pertecene(parametro, listParametros)) {
				parametroAux = new Parametro(parametro.getNombre(), parametro.getDescripcion());
				listParametros.add(parametroAux);
				
			}
		}		
		return listParametros;
	}
	
	public Parametro consultarParametro(String nombre, int idProyecto) {
		Parametro parametro = parametroDAO.consultarParametro(nombre, idProyecto);
		return parametro;
	}
	
	private static boolean pertecene(Parametro parametro,
			ArrayList<Parametro> listParametros) {
		for (Parametro parametroi : listParametros) {
			if (parametroi.getNombre().equals(parametro.getNombre())) {
				return true;
			}
		}
		return false;
	}
	
	public List<Parametro> consultarParametros(int idProyecto) {
		List<Parametro> listParametros = parametroDAO.consultarParametros(idProyecto);
		return listParametros;
	}
}

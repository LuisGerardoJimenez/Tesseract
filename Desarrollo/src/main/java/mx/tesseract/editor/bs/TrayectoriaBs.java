package mx.tesseract.editor.bs;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.enums.ReferenciaEnum;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.enums.ReferenciaEnum.TipoCatalogo;
import mx.tesseract.enums.ReferenciaEnum.TipoSeccion;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.dto.TrayectoriaDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.dao.MensajeParametroDAO;
import mx.tesseract.editor.dao.ParametroDAO;
import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.Mensaje;
import mx.tesseract.editor.entidad.MensajeParametro;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.editor.entidad.Parametro;
import mx.tesseract.editor.entidad.Paso;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.TerminoGlosario;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.editor.entidad.Verbo;
import mx.tesseract.util.JsonUtil;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("trayectoriaBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TrayectoriaBs {

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
	private ParametroDAO parametroDAO;
	
	@Autowired
	private MensajeParametroDAO mensajeParametroDAO;

	/*LO NUEVO*/
	@Autowired
	private TokenBs tokenBs;
	
	@Autowired
	private CatalogoBs catalogoBs;
	
	public boolean existeTrayectoriaPrincipal(int idCU) {
		CasoUso casoUso = genericoDAO.findById(CasoUso.class, idCU);
		for (Trayectoria t : casoUso.getTrayectorias()) {
			if (!t.isAlternativa()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean existeTrayectoriaPrincipal(int idCU, int idTray) {
		CasoUso casoUso = genericoDAO.findById(CasoUso.class, idCU);
		for (Trayectoria t : casoUso.getTrayectorias()) {
			if (!t.isAlternativa() && t.getId() != idTray) {
				return true;
			}
		}
		return false;
	}
	
	/*MENSJAE SBORRAR CUANDO SE TERMINE ESTO*/
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

	public TrayectoriaDTO buscaElementos(Integer idProyecto, Integer idCU) {
		TrayectoriaDTO trayectoriaDTO = new TrayectoriaDTO();
		// Lists de los elementos disponibles
		List<Elemento> listElementos;
		List<ReglaNegocio> listReglasNegocio = new ArrayList<ReglaNegocio>();
		List<Entidad> listEntidades = new ArrayList<Entidad>();
		List<CasoUso> listCasosUso = new ArrayList<CasoUso>();
		List<Pantalla> listPantallas = new ArrayList<Pantalla>();
		List<Mensaje> listMensajes = new ArrayList<Mensaje>();
		List<Actor> listActores = new ArrayList<Actor>();
		List<TerminoGlosario> listTerminosGls = new ArrayList<TerminoGlosario>();
		List<Atributo> listAtributos = new ArrayList<Atributo>();
		List<Paso> listPasos = new ArrayList<Paso>();
		List<Trayectoria> listTrayectorias = new ArrayList<Trayectoria>();
		List<Accion> listAcciones = new ArrayList<Accion>();

		// Se consultan los elementos de todo el proyecto
		listElementos = elementoDAO.findAllByIdProyecto(idProyecto);

		// Módulo auxiliar para la serialización
		Modulo moduloAux = null;

		if (listElementos != null && !listElementos.isEmpty()) {
			// Se clasifican los conjuntos
			for (Elemento el : listElementos) {
				switch (ReferenciaEnum.getTipoReferencia(el)) {

				case ACTOR:
					Actor auxActor = new Actor();
					auxActor.setClave(el.getClave());
					auxActor.setNombre(el.getNombre());
					listActores.add(auxActor);
					break;
				case CASOUSO:					
					CasoUso auxCasoUso = new CasoUso();
					CasoUso cu = (CasoUso) el;
					
					moduloAux = new Modulo();
					moduloAux.setId(cu.getModulo().getId());
					moduloAux.setNombre(cu.getModulo().getNombre());
					moduloAux.setClave(cu.getModulo().getClave());
					
					auxCasoUso.setClave(cu.getClave());
					auxCasoUso.setNumero(cu.getNumero());
					auxCasoUso.setNombre(cu.getNombre());
					auxCasoUso.setModulo(moduloAux);
					listCasosUso.add(auxCasoUso);

					// Se obtienen las Trayectorias
					List<Trayectoria> trayectorias = ((CasoUso) el)
							.getTrayectorias();
					for (Trayectoria tray : trayectorias) {
						if (tray.getCasoUso().getId() == idCU) {
							Trayectoria auxTrayectoria = new Trayectoria();
							auxTrayectoria.setClave(tray.getClave());
							auxTrayectoria.setCasoUso(auxCasoUso);
							listTrayectorias.add(auxTrayectoria);
							// Se obtienen los Pasos
							List<Paso>pasos = tray.getPasos();//HOLI
						//	Set<Paso> pasos = tray.getPasos();
							for (Paso paso : pasos) {
								Paso auxPaso = new Paso();
								auxPaso.setTrayectoria(auxTrayectoria);
								auxPaso.setNumero(paso.getNumero());
								auxPaso.setRealizaActor(paso.isRealizaActor());
								auxPaso.setVerbo(paso.getVerbo());
								auxPaso.setOtroVerbo(paso.getOtroVerbo());
								auxPaso.setRedaccion(tokenBs
										.decodificarCadenaSinToken(paso
												.getRedaccion()));
								listPasos.add(auxPaso);
							}
						}
					}
					break;
				case ENTIDAD:
					Entidad auxEntidad = new Entidad();
					auxEntidad.setNombre(el.getNombre());
					listEntidades.add(auxEntidad);
					// Se obtienen los Atributos
					List<Atributo> atributos = ((Entidad) el).getAtributos();
					for (Atributo atributo : atributos) {
						Atributo auxAtributo = new Atributo();
						auxAtributo.setEntidad(auxEntidad);
						auxAtributo.setNombre(atributo.getNombre());
						listAtributos.add(auxAtributo);
					}

					break;
				case MENSAJE:
					Mensaje auxMensaje = new Mensaje();
					auxMensaje.setNumero(el.getNumero());
					auxMensaje.setNombre(el.getNombre());
					listMensajes.add(auxMensaje);
					break;
				case PANTALLA:
					Pantalla auxPantalla = new Pantalla();
					Pantalla pantalla = (Pantalla) el;
					moduloAux = new Modulo();
					moduloAux.setId(pantalla.getModulo().getId());
					moduloAux.setNombre(pantalla.getModulo().getNombre());
					moduloAux.setClave(pantalla.getModulo().getClave());
					
					auxPantalla.setClave(pantalla.getClave());
					auxPantalla.setNumero(pantalla.getNumero());
					auxPantalla.setNombre(pantalla.getNombre());
					auxPantalla.setModulo(moduloAux);
					listPantallas.add(auxPantalla);
					// Se obtienen las acciones
					List<Accion> acciones = ((Pantalla) el).getAcciones();
					for (Accion accion : acciones) {
						Accion auxAccion = new Accion();
						auxAccion.setPantalla(auxPantalla);
						auxAccion.setNombre(accion.getNombre());
						listAcciones.add(auxAccion);
					}
					break;
				case REGLANEGOCIO:
					ReglaNegocio auxReglaNegocio = new ReglaNegocio();
					auxReglaNegocio.setNumero(el.getNumero());
					auxReglaNegocio.setNombre(el.getNombre());
					listReglasNegocio.add(auxReglaNegocio);
					break;
				case TERMINOGLS:
					TerminoGlosario auxTerminoGlosario = new TerminoGlosario();
					auxTerminoGlosario.setNombre(el.getNombre());
					listTerminosGls.add(auxTerminoGlosario);
					break;
				default:
					break;
				}
			}

			// Se convierte en json las Reglas de Negocio
			if (listReglasNegocio != null) {
				trayectoriaDTO.setJsonReglasNegocio(JsonUtil
						.mapListToJSON(listReglasNegocio));
			}
			// Se convierte en json las Entidades
			if (listEntidades != null) {
				trayectoriaDTO.setJsonEntidades(JsonUtil.mapListToJSON(listEntidades));
			}
			// Se convierte en json los Casos de Uso
			if (listCasosUso != null) {
				trayectoriaDTO.setJsonCasosUsoProyecto(JsonUtil
						.mapListToJSON(listCasosUso));
			}
			// Se convierte en json las Pantallas
			if (listPantallas != null) {
				trayectoriaDTO.setJsonPantallas(JsonUtil.mapListToJSON(listPantallas));
			}
			// Se convierte en json los Mensajes
			if (listMensajes != null) {
				trayectoriaDTO.setJsonMensajes(JsonUtil.mapListToJSON(listMensajes));
			}
			// Se convierte en json los Actores
			if (listActores != null) {
				trayectoriaDTO.setJsonActores(JsonUtil.mapListToJSON(listActores));
			}
			// Se convierte en json los Términos del Glosario
			if (listTerminosGls != null) {
				trayectoriaDTO.setJsonTerminosGls(JsonUtil.mapListToJSON(listTerminosGls));
			}
			// Se convierte en json los Atributos
			if (listAtributos != null) {
				trayectoriaDTO.setJsonAtributos(JsonUtil.mapListToJSON(listAtributos));
			}
			// Se convierte en json los Pasos
			if (listPasos != null) {
				trayectoriaDTO.setJsonPasos(JsonUtil.mapListToJSON(listPasos));
			}
			// Se convierte en json las Trayectorias
			if (listTrayectorias != null) {
				trayectoriaDTO.setJsonTrayectorias(JsonUtil
						.mapListToJSON(listTrayectorias));
			}
			// Se convierte en json las Acciones
			if (listAcciones != null) {
				trayectoriaDTO.setJsonAcciones(JsonUtil.mapListToJSON(listAcciones));
			}
		}
		return trayectoriaDTO;
	}
	
	public List<String> consultarVerbos() {
		List<Verbo> lv = genericoDAO.findAll(Verbo.class);
		if (lv == null) {
			throw new TESSERACTException("No se pueden consultar los verbos.",
					"MSG13");
		}
		catalogoBs.opcionOtro(lv, TipoCatalogo.VERBO);

		List<String> verbos = new ArrayList<String>();
		for (Verbo v : lv) {
			verbos.add(v.getNombre());
		}
		return verbos;
	}

	public void preAlmacenarObjetosToken(Trayectoria model, Integer idModulo) {
		List<Paso> pasos = model.getPasos();

		for (Paso paso : pasos) {
			tokenBs.almacenarObjetosToken(
					tokenBs.convertirToken_Objeto(paso.getRedaccion(),
							model.getCasoUso().getProyecto(), idModulo),
					TipoSeccion.PASOS, paso);
			paso.setRedaccion(tokenBs.codificarCadenaToken(paso.getRedaccion(),
					model.getCasoUso().getProyecto(), idModulo));

		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarTrayectoria(TrayectoriaDTO model, Integer idCasoUso) {
		if (rn006.isValidRN006(model, idCasoUso)) {
			Trayectoria entidad = new Trayectoria();
			entidad.setClave(model.getClave());
			entidad.setAlternativa(model.isAlternativa());
			entidad.setCasoUso(entidad.getCasoUso());
			entidad.setCondicion(model.getCondicion());
			entidad.setFinCasoUso(model.isFinCasoUso());
			genericoDAO.save(entidad);
		} else {
			throw new TESSERACTValidacionException("La clave de la Trayectoria ya existe.", "MSG7",
					new String[] { "La", "Trayectoria", model.getClave() }, "model.clave");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarTrayectoria(TrayectoriaDTO model) {
		if (rn006.isValidRN006(model, model.getCasoUso().getId())) {
			Trayectoria entidad = genericoDAO.findById(Trayectoria.class, model.getId());
			entidad.setClave(model.getClave());
			entidad.setCondicion(model.getCondicion());
			entidad.setFinCasoUso(model.isFinCasoUso());
			genericoDAO.update(entidad);
		} else {
			throw new TESSERACTValidacionException("La clave de la Trayectoria ya existe.", "MSG7",
					new String[] { "La", "Trayectoria", model.getClave() }, "model.clave");
		}
	}

	public TrayectoriaDTO consultarTrayectoriaById(Integer id) {
		Trayectoria trayectoria = genericoDAO.findById(Trayectoria.class, id);
		TrayectoriaDTO trayectoriaDTO = new TrayectoriaDTO();
		if (trayectoria != null) {
			trayectoriaDTO.setClave(trayectoria.getClave());
			trayectoriaDTO.setCondicion(trayectoria.getCondicion());
			trayectoriaDTO.setFinCasoUso(trayectoria.isFinCasoUso());
			trayectoriaDTO.setId(trayectoria.getId());
			trayectoriaDTO.setAlternativa(trayectoria.isAlternativa());
			trayectoriaDTO.setCasoUso(trayectoria.getCasoUso());
		} else {
			throw new TESSERACTException("No se puede consultar la trayectoria.", "MSG12");
		}
		return trayectoriaDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarTrayectoria(TrayectoriaDTO model) {
		if (rn018.isValidRN018(model)) {
			Trayectoria entidad = genericoDAO.findById(Trayectoria.class, model.getId());
			genericoDAO.delete(entidad);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}
}
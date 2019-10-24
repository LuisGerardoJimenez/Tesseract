package mx.tesseract.dto;

public class PasoDTO {
	
	private Integer id;
	private Integer numero;
	private Boolean realizaActor;
	private String redaccion;
	private Integer idTrayectoria;
	private Integer idVerbo;
	private String otroVerbo;
	private String verbo;
	//private List<ReferenciaParametro> referencias = new ArrayList<ReferenciaParametro>();


	public PasoDTO() {
	}

	public PasoDTO(Integer id, Integer numero, Boolean realizaActor, String redaccion,
			Integer idTrayectoria, Integer idVerbo, String verbo, String otroVerbo) {
		this.id = id;
		this.numero = numero;
		this.realizaActor = realizaActor;
		this.redaccion = redaccion;
		this.idTrayectoria = idTrayectoria;
		this.idVerbo = idVerbo;
		this.verbo = verbo;
		this.otroVerbo = otroVerbo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Boolean getRealizaActor() {
		return realizaActor;
	}

	public void setRealizaActor(Boolean realizaActor) {
		this.realizaActor = realizaActor;
	}

	public String getRedaccion() {
		return redaccion;
	}

	public void setRedaccion(String redaccion) {
		this.redaccion = redaccion;
	}
	
	public Integer getIdTrayectoria() {
		return idTrayectoria;
	}
	
	public void setIdTrayectoria(Integer idTrayectoria) {
		this.idTrayectoria = idTrayectoria;
	}

	public Integer getIdVerbo() {
		return idVerbo;
	}

	public void setIdVerbo(Integer idVerbo) {
		this.idVerbo = idVerbo;
	}

	public String getOtroVerbo() {
		return otroVerbo;
	}

	public void setOtroVerbo(String otroVerbo) {
		this.otroVerbo = otroVerbo;
	}

	public String getVerbo() {
		return verbo;
	}

	public void setVerbo(String verbo) {
		this.verbo = verbo;
	}
	
}

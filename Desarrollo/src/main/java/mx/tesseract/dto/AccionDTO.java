package mx.tesseract.dto;

public class AccionDTO {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer idTipoAccion;
	private Integer idPantallaDestino;
	
	
	public AccionDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdTipoAccion() {
		return idTipoAccion;
	}

	public void setIdTipoAccion(Integer idTipoAccion) {
		this.idTipoAccion = idTipoAccion;
	}

	public Integer getIdPantallaDestino() {
		return idPantallaDestino;
	}

	public void setIdPantallaDestino(Integer idPantallaDestino) {
		this.idPantallaDestino = idPantallaDestino;
	}

}

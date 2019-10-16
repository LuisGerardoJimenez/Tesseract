package mx.tesseract.dto;

public class SelectDTO {
	private Boolean valor;
	private String nombre;
	
	public SelectDTO(Boolean valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}

	public Boolean getValor() {
		return valor;
	}

	public void setValor(Boolean valor) {
		this.valor = valor;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}

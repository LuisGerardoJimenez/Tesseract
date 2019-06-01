package mx.tesseract.entidad;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact {
	@EmbeddedId
	private ContactId id;
	@Column(name = "id_person", insertable = false, updatable = false)
	private Integer idPerson;
	@Column(name = "id_contact", insertable = false, updatable = false)
	private Integer idContact;

	public Contact() {
		super();
	}

	public Contact(Integer idPerson, Integer idContact) {
		super();
		this.idPerson = idPerson;
		this.idContact = idContact;
		this.id = new ContactId(idPerson, idContact);
	}

	public Integer getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

	public Integer getIdContact() {
		return idContact;
	}

	public void setIdContact(Integer idContact) {
		this.idContact = idContact;
	}
}

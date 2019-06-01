package mx.tesseract.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "access")
public class Access {
	@Id
	@SequenceGenerator(name = "access_id_access_seq", sequenceName = "access_id_access_seq", allocationSize = 1)
	@GeneratedValue(generator = "access_id_access_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_access")
	private Integer id;
	@Column(name = "nu_attempt")
	private Integer attempt;
	@Column(name = "fh_failed")
	private Date lastFailed;
	@Column(name = "fh_lock")
	private Date lock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAttempt() {
		return attempt;
	}

	public void setAttempt(Integer attempt) {
		this.attempt = attempt;
	}

	public Date getLastFailed() {
		return lastFailed;
	}

	public void setLastFailed(Date lastFailed) {
		this.lastFailed = lastFailed;
	}

	public Date getLock() {
		return lock;
	}

	public void setLock(Date lock) {
		this.lock = lock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Access other = (Access) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

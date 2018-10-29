package models;

import java.util.Date;
import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medium_ausleihen")
public class MediumAusleihen implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ausleih_id")
	private int ausleihId;
	
	@Column(name="id_medium")
	private int idMedium;
	
	@Column(name="id_ausleiher")
	private int idAusleiher;
	
	@Column(name="ausleih_datum")
	private Date ausleihDatum;

	public MediumAusleihen() {}

	
	public int getAusleihId() {
		return ausleihId;
	}

	public void setAusleihId(int ausleihId) {
		this.ausleihId = ausleihId;
	}

	public int getIdMedium() {
		return idMedium;
	}

	public void setIdMedium(int idMedium) {
		this.idMedium = idMedium;
	}

	public int getIdAusleiher() {
		return idAusleiher;
	}

	public void setIdAusleiher(int idAusleiher) {
		this.idAusleiher = idAusleiher;
	}

	public Date getAusleihDatum() {
		return ausleihDatum;
	}

	public void setAusleihDatum(Date ausleihDatum) {
		this.ausleihDatum = ausleihDatum;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: ausleihId={1}, idMedium={2}, idAusleiher={3}, ausleihDatum={4}", 
				new Object[] {getClass().getSimpleName(), ausleihId, idMedium, idAusleiher, ausleihDatum});
	}
	
	
	
}

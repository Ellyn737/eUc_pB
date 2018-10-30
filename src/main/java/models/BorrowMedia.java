package models;

import java.sql.Date;
import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * class for building the borrowing in the database
 * 
 * @author ellyn
 */
@Entity
@Table(name="borrow_media", schema="ellynhb")
public class BorrowMedia implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="borrow_id")
	private int borrowId;
	
	@Column(name="id_media")
	private int idMedia;
	
	@Column(name="id_lender")
	private int idLender;
	
	@Column(name="return_date")
	private Date returnDate;
	
	@Column(name="message")
	private String message;
	
	@Column(name="is_returned")
	private Boolean isReturned;

	public BorrowMedia() {}

	public int getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(int idMedia) {
		this.idMedia = idMedia;
	}

	public int getIdLender() {
		return idLender;
	}

	public void setIdLender(int idLender) {
		this.idLender = idLender;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date rDate) {
		this.returnDate = rDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(Boolean isReturned) {
		this.isReturned = isReturned;
	}

	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: ausleihId={1}, idMedium={2}, idAusleiher={3}, ausleihDatum={4}", 
				new Object[] {getClass().getSimpleName(), borrowId, idMedia, idLender, returnDate});
	}
	
	
	
}

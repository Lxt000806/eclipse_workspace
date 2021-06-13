package com.house.home.entity.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tXTDM")
public class Xtdm extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID",nullable = false)
	private String id;
	@Id
	@Column(name = "CBM",nullable = false)
	private String cbm;
	@Id
	@Column(name = "IBM",nullable = false)
	private Integer ibm;
	@Column(name = "IDNOTE")
	private String idnote;
	@Column(name = "NOTE")
	private String note;
	@Column(name = "NOTE_E")
	private String noteE;
	@Column(name = "IDNOTE_E")
	private String idnoteE;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "Expired")
	private String expired;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCbm() {
		return cbm;
	}
	public void setCbm(String cbm) {
		this.cbm = cbm;
	}
	public Integer getIbm() {
		return ibm;
	}
	public void setIbm(Integer ibm) {
		this.ibm = ibm;
	}
	public String getIdnote() {
		return idnote;
	}
	public void setIdnote(String idnote) {
		this.idnote = idnote;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNoteE() {
		return noteE;
	}
	public void setNoteE(String noteE) {
		this.noteE = noteE;
	}
	public String getIdnoteE() {
		return idnoteE;
	}
	public void setIdnoteE(String idnoteE) {
		this.idnoteE = idnoteE;
	}
	
	public Integer getDispSeq() {
		if(dispSeq==null){
			return 0;
		}
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cbm == null) ? 0 : cbm.hashCode());
		result = prime * result + ((ibm == null) ? 0 : ibm.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idnote == null) ? 0 : idnote.hashCode());
		result = prime * result + ((idnoteE == null) ? 0 : idnoteE.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((noteE == null) ? 0 : noteE.hashCode());
		result = prime * result + ((dispSeq == null) ? 0 : dispSeq.hashCode());
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
		Xtdm other = (Xtdm) obj;
		if (cbm == null) {
			if (other.cbm != null)
				return false;
		} else if (!cbm.equals(other.cbm))
			return false;
		if (ibm == null) {
			if (other.ibm != null)
				return false;
		} else if (!ibm.equals(other.ibm))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idnote == null) {
			if (other.idnote != null)
				return false;
		} else if (!idnote.equals(other.idnote))
			return false;
		if (idnoteE == null) {
			if (other.idnoteE != null)
				return false;
		} else if (!idnoteE.equals(other.idnoteE))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (noteE == null) {
			if (other.noteE != null)
				return false;
		} else if (!noteE.equals(other.noteE))
			return false;
		if (dispSeq == null) {
			if (other.dispSeq != null)
				return false;
		} else if (!dispSeq.equals(other.dispSeq))
			return false;
		return true;
	}

}

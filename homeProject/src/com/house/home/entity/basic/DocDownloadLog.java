package com.house.home.entity.basic;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tDocDownloadLog")
public class DocDownloadLog extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "DocPK")
	private Integer docPK;
	@Column(name = "AttachmentPK")
	private Integer attachmentPK;
	@Column(name = "DownloadDate")
	private Date downloadDate;
	@Column(name = "DownloadCZY")
	private String downloadCZY;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getDocPK() {
		return docPK;
	}
	public void setDocPK(Integer docPK) {
		this.docPK = docPK;
	}
	public Integer getAttachmentPK() {
		return attachmentPK;
	}
	public void setAttachmentPK(Integer attachmentPK) {
		this.attachmentPK = attachmentPK;
	}
	public Date getDownloadDate() {
		return downloadDate;
	}
	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}
	public String getDownloadCZY() {
		return downloadCZY;
	}
	public void setDownloadCZY(String downloadCZY) {
		this.downloadCZY = downloadCZY;
	}


}

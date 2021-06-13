package com.house.home.entity.workflow;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActByteArray信息
 */
@Entity
@Table(name = "ACT_GE_BYTEARRAY")
public class ActByteArray extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "DEPLOYMENT_ID_")
	private String deploymentId;
	@Column(name = "BYTES_")
	private Blob bytes;
	@Column(name = "GENERATED_")
	private Boolean generated;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	public Integer getRev() {
		return this.rev;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	public String getDeploymentId() {
		return this.deploymentId;
	}
	public void setBytes(Blob bytes) {
		this.bytes = bytes;
	}
	
	public Blob getBytes() {
		return this.bytes;
	}
	public void setGenerated(Boolean generated) {
		this.generated = generated;
	}
	
	public Boolean getGenerated() {
		return this.generated;
	}

}

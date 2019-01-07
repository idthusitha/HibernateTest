package com.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(schema = "rezbase_v3_reports", name = "report_labels")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "report")
public class ReportLabel extends BasePojo {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "label_key")
	private String labelKey;

	@Column(name = "status")
	private String status;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ReportLabelMapping", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "report_labels_id") })
	private Set<ReportLabelMapping> reportLabelMappingSet = new HashSet<ReportLabelMapping>(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<ReportLabelMapping> getReportLabelMappingSet() {
		return reportLabelMappingSet;
	}

	public void setReportLabelMappingSet(Set<ReportLabelMapping> reportLabelMappingSet) {
		this.reportLabelMappingSet = reportLabelMappingSet;
	}

}

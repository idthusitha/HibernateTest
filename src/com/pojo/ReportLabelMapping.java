package com.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

@Entity
@Table(schema = "rezbase_v3_reports", name = "report_labels_mapping")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "report")
@NamedQuery(name = "ReportLabelMapping.findAll", query = "SELECT r FROM ReportLabelMapping r")
public class ReportLabelMapping extends BasePojo {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="language_id",referencedColumnName="id")
	private Language language;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="report_id",referencedColumnName="id")
	private ReportName reportName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="report_labels_id",referencedColumnName="id")
	private ReportLabel reportLabel;

	@Column(name = "label_value")
	private String labelValue;

	@Column(name = "status")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public ReportName getReportName() {
		return reportName;
	}

	public void setReportName(ReportName reportName) {
		this.reportName = reportName;
	}

	public ReportLabel getReportLabel() {
		return reportLabel;
	}

	public void setReportLabel(ReportLabel reportLabel) {
		this.reportLabel = reportLabel;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}

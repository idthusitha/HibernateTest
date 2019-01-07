package com.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(schema = "rezbase_v3_reports", name = "language")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "report")
public class Language extends BasePojo {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "languagecode")
	private String languageCode;

	@Column(name = "languagename")
	private String languageName;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ReportLabelMapping", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = { @JoinColumn(name = "language_id") })
	private Set<ReportLabelMapping> reportLabelMappingSet = new HashSet<ReportLabelMapping>(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public Set<ReportLabelMapping> getReportLabelMappingSet() {
		return reportLabelMappingSet;
	}

	public void setReportLabelMappingSet(Set<ReportLabelMapping> reportLabelMappingSet) {
		this.reportLabelMappingSet = reportLabelMappingSet;
	}
}

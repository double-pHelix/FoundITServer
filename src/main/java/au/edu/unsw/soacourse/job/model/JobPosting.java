package au.edu.unsw.soacourse.job.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobPosting {
    //job post status
    public static final String STATUS_OPEN = "open";
    public static final String STATUS_CLOSED = "closed";
    //archived status
    public static final String ARCHIVED_TRUE = "T";
    public static final String ARCHIVED_FALSE = "F";
    
	private String id;
	private String title;

	private String description;
    private String companyProfileId;
    private CompanyProfile companyProfileLink;
	private String positionType;
	private String skills;
    private String salaryLevel;
    private String location;
	private String status; //(created, open, in-review, processed, sent invitations)
	private String archived;
    private String link;
	private String rel;
	
	public JobPosting() {
		super();
	}
	
	public JobPosting(String id) {
		super();
		this.id = id;
		this.status = STATUS_OPEN;
		this.archived = ARCHIVED_FALSE;
	}
	

	public JobPosting(String id, String title, String description,
			String companyProfileId, String positionType, String desiredSkills,
			String salaryLevel, String location) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.companyProfileId = companyProfileId;
		this.positionType = positionType;
		this.skills = desiredSkills;
		this.salaryLevel = salaryLevel;
		this.location = location;
		this.status = STATUS_OPEN;
		this.archived = ARCHIVED_FALSE;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompanyProfileId() {
		return companyProfileId;
	}
	public void setCompanyProfileId(String companyProfileId) {
		this.companyProfileId = companyProfileId;
	}
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getSalaryLevel() {
		return salaryLevel;
	}
	public void setSalaryLevel(String salaryLevel) {
		this.salaryLevel = salaryLevel;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getArchived() {
		return archived;
	}
	public void setArchived(String archived) {
		this.archived = archived;
	}

	@XmlAttribute(name = "href")
	public String getLink() {
		if(this.id == null)
			return link;
			
		this.link = "http://localhost:8080/RestfulJobService/jobposting/" + this.id;
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@XmlAttribute(name = "rel")
	public String getRel() {
		rel = "hiringteam";
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}

	@XmlElement(name = "companyProfile")
	public CompanyProfile getCompanyProfileLink() {
		this.companyProfileLink = new CompanyProfile(this.companyProfileId);
		return companyProfileLink;
	}

	public void setCompanyProfileLink(CompanyProfile companyProfileLink) {
		this.companyProfileLink = companyProfileLink;
	}
	
	
}

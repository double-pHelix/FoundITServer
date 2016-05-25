package au.edu.unsw.soacourse.job.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobPosting {
	private String id;
    private String description;
    private String companyProfileId;
	private String positionType;
	private String desiredSkills;
    private String salaryLevel;
    private String location;
	private String status; //(created, open, in-review, processed, sent invitations)
	private String archived;
	
	public JobPosting() {
		super();
	}
	
	public JobPosting(String id) {
		super();
		this.id = id;
		this.description = "PLEASE ENTER";
		this.companyProfileId = "PLEASE ENTER";
		this.positionType = "PLEASE ENTER";
		this.desiredSkills = "PLEASE ENTER";
		this.salaryLevel = "PLEASE ENTER";
		this.location = "PLEASE ENTER";
		this.status = "PLEASE ENTER";
		this.archived = "PLEASE ENTER";
	}
	
	
	public JobPosting(String id, String description, String companyProfileId,
			String positionType, String desiredSkills, String salaryLevel,
			String location) {
		super();
		this.id = id;
		this.description = description;
		this.companyProfileId = companyProfileId;
		this.positionType = positionType;
		this.desiredSkills = desiredSkills;
		this.salaryLevel = salaryLevel;
		this.location = location;
		this.status = "default";
		this.archived = "N";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDesiredSkills() {
		return desiredSkills;
	}
	public void setDesiredSkills(String desiredSkills) {
		this.desiredSkills = desiredSkills;
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
	
	
}

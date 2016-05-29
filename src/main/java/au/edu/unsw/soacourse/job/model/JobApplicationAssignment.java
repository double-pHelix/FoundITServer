package au.edu.unsw.soacourse.job.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobApplicationAssignment {
	private String id;
    private String jobApplicationId;
    private JobApplication jobApplicationLink;
    private String reviewer1;
    private TeamMemberProfile reviewer1Link;
	private String reviewer2;
	private TeamMemberProfile reviewer2Link;
	private String link;
	private String rel;
	private boolean sendVersion = false;
	public void setSendVersion(boolean set){
		this.sendVersion = set;
	}
	
	public JobApplicationAssignment() {
		super();
	}

	public JobApplicationAssignment(String id, String jobApplicationId,
			String reviewer1, String reviewer2) {
		super();
		this.id = id;
		this.jobApplicationId = jobApplicationId;
		this.reviewer1 = reviewer1;
		this.reviewer2 = reviewer2;
		this.link = "http://localhost:8080/RestfulJobService/jobappreviewassign/" + this.id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		this.link = "http://localhost:8080/RestfulJobService/jobappreviewassign/" + this.id;
	}
	public String getJobApplicationId() {
		if(sendVersion == true)
			return null;
		return jobApplicationId;
	}
	public void setJobApplicationId(String jobApplicationId) {
		this.jobApplicationId = jobApplicationId;
	}
	public String getReviewer1() {
		if(sendVersion == true)
			return null;
		return reviewer1;
	}
	public void setReviewer1(String reviewer1) {
		this.reviewer1 = reviewer1;
	}
	public String getReviewer2() {
		if(sendVersion == true)
			return null;
		return reviewer2;
	}
	public void setReviewer2(String reviewer2) {
		this.reviewer2 = reviewer2;
	}

	@XmlAttribute(name = "href")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
		this.id = link.substring(link.length()-1);
	}

	@XmlAttribute(name = "rel")
	public String getRel() {
		rel = "jobappassign";
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}

	@XmlElement(name = "jobApplication")
	public JobApplication getJobApplicationLink() {
		if(this.jobApplicationId == null)
			return null;
		
		this.jobApplicationLink = new JobApplication(this.jobApplicationId);
		return jobApplicationLink;
	}

	public void setJobApplicationLink(JobApplication jobApplicationLink) {
		this.jobApplicationLink = jobApplicationLink;
	}

	public TeamMemberProfile getReviewer1Link() {
		if(this.reviewer1 == null)
			return null;
		
		this.reviewer1Link = new TeamMemberProfile(this.reviewer1);
		
		return reviewer1Link;
	}

	public void setReviewer1Link(TeamMemberProfile reviewer1Link) {
		this.reviewer1Link = reviewer1Link;
	}

	public TeamMemberProfile getReviewer2Link() {
		if(this.reviewer2 == null)
			return null;
		
		this.reviewer2Link = new TeamMemberProfile(this.reviewer2);
		
		return reviewer2Link;
	}

	public void setReviewer2Link(TeamMemberProfile reviewer2Link) {
		this.reviewer2Link = reviewer2Link;
	}

	
	
	
}

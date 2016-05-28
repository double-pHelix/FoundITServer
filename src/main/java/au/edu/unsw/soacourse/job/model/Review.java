package au.edu.unsw.soacourse.job.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Review {
    public static final String DECISION_ACCEPTED = "accepted";
    public static final String DECISION_REJECTED = "rejected";
    
	private String id;
    private String teamMemberProfileId;
    private TeamMemberProfile teamMemberProfileLink;
    
    private String jobApplicationId;
    private JobApplication jobApplicationLink;
	private String comments;
	private String decision; // (accepted/rejected)
	private String link;
	private String rel;
	
	public Review() {
		super();
	}
	
	public Review(String id, String teamMemberProfileId,
			String jobApplicationId, String comments, String decision) {
		super();
		this.id = id;
		this.teamMemberProfileId = teamMemberProfileId;
		this.jobApplicationId = jobApplicationId;
		this.comments = comments;
		this.decision = decision;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTeamMemberProfileId() {
		return teamMemberProfileId;
	}
	public void setTeamMemberProfileId(String teamMemberProfileId) {
		this.teamMemberProfileId = teamMemberProfileId;
	}
	public String getJobApplicationId() {
		return jobApplicationId;
	}
	public void setJobApplicationId(String jobApplicationId) {
		this.jobApplicationId = jobApplicationId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	
	@XmlAttribute(name = "href")
	public String getLink() {
		this.link = "http://localhost:8080/RestfulJobService/review/" + this.id;
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

	@XmlElement(name = "teamMemberProfile")
	public TeamMemberProfile getTeamMemberProfileLink() {
		this.teamMemberProfileLink = new TeamMemberProfile(this.id);
		return teamMemberProfileLink;
	}

	public void setTeamMemberProfileLink(TeamMemberProfile teamMemberProfileLink) {
		this.teamMemberProfileLink = teamMemberProfileLink;
	}

	@XmlElement(name = "jobApplication")
	public JobApplication getJobApplicationLink() {
		this.jobApplicationLink = new JobApplication(this.id);
		return jobApplicationLink;
	}

	public void setJobApplicationLink(JobApplication jobApplicationLink) {
		this.jobApplicationLink = jobApplicationLink;
	}
	
	
}

package au.edu.unsw.soacourse.job.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Review {
    public static final String DECISION_ACCEPTED = "accepted";
    public static final String DECISION_REJECTED = "rejected";
    
	private String id;
    private String teamMemberProfileId;
    private String teamMemberProfileLink;
    private String jobApplicationId;
    private String jobApplicationLink;
	private String comments;
	private String decision; // (accepted/rejected)
	private String link;
	
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
		this.link = "http://localhost:8080/RestfulJobService/foundIT/review/" + this.id;
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
	public String getLink() {
		this.link = "http://localhost:8080/RestfulJobService/foundIT/review/" + this.id;
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public String getTeamMemberProfileLink() {
		this.teamMemberProfileLink = "http://localhost:8080/RestfulJobService/foundIT/teammemberprofile/" + this.id;
		return teamMemberProfileLink;
	}

	public void setTeamMemberProfileLink(String teamMemberProfileLink) {
		this.teamMemberProfileLink = teamMemberProfileLink;
	}

	public String getJobApplicationLink() {
		this.jobApplicationLink = "http://localhost:8080/RestfulJobService/foundIT/jobapplication/" + this.id;
		return jobApplicationLink;
	}

	public void setJobApplicationLink(String jobApplicationLink) {
		this.jobApplicationLink = jobApplicationLink;
	}
	
	
}

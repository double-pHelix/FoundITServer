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
	private boolean sendVersion = false;
	public void setSendVersion(boolean set){
		this.sendVersion = set;
	}
	
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
		this.link = "http://localhost:8080/FoundITServer/review/" + this.id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTeamMemberProfileId() {
		if(sendVersion == true)
			return null;
		return teamMemberProfileId;
	}
	public void setTeamMemberProfileId(String teamMemberProfileId) {
		this.teamMemberProfileId = teamMemberProfileId;
	}
	public String getJobApplicationId() {
		if(sendVersion == true)
			return null;
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
		return link;
	}

	public void setLink(String link) {
		this.link = link;
		this.id = link.substring(link.length()-1);
	}

	@XmlAttribute(name = "rel")
	public String getRel() {
		rel = "review";
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}

	@XmlElement(name = "teamMemberProfile")
	public TeamMemberProfile getTeamMemberProfileLink() {
		if(this.teamMemberProfileId == null)
			return null;
		
		this.teamMemberProfileLink = new TeamMemberProfile(this.teamMemberProfileId);
		return teamMemberProfileLink;
	}

	public void setTeamMemberProfileLink(TeamMemberProfile teamMemberProfileLink) {
		this.teamMemberProfileLink = teamMemberProfileLink;
		this.teamMemberProfileId = teamMemberProfileLink.getId();
		
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
		this.jobApplicationId = jobApplicationLink.getId();
	}
	
	
}

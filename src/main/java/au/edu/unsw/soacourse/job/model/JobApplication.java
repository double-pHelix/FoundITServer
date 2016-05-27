package au.edu.unsw.soacourse.job.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobApplication {
	
   //job app status
    public static final String STATUS_SUBMITTED = "submitted";
    public static final String STATUS_SHORTLISTED = "shortlisted";
    public static final String STATUS_NOT_SHORTLISTED = "not-shortlisted";
    public static final String STATUS_ACCEPTED = "accepted-invitation";
    public static final String STATUS_REJECTED = "rejected-invitation";
    //archived status
    public static final String ARCHIVED_TRUE = "T";
    public static final String ARCHIVED_FALSE = "F";
    
    
	private String id;
    private String jobPostId;
    private String jobPostLink;
    private String userProfileId;
    private String userProfileLink;
    private String coverLetter;
    private String resume; //Maybe just a text pretending to be an attachment (i.e. “resume.pdf”)
    private String status; // (shortlisted/not-shortlisted or rejected/accepted)
    private String archived; //Y or N
    private String link;
    
    public JobApplication() {

	}
    
    public JobApplication(String id) {
		super();
		this.id = id;
		this.jobPostId = "PLEASE ENTER";
		this.userProfileId = "PLEASE ENTER";
		this.coverLetter = "PLEASE ENTER";
		this.resume = "PLEASE ENTER";
		this.status = STATUS_SUBMITTED;
		this.archived = ARCHIVED_FALSE;
		this.link = "http://localhost:8080/RestfulJobService/foundIT/jobapplication/" + this.id;
	}
    
    public JobApplication(String id, String jobApplicationId,
			String userProfileId, String coverLetter, String resume) {
		super();
		this.id = id;
		this.jobPostId = jobApplicationId;
		this.userProfileId = userProfileId;
		this.coverLetter = coverLetter;
		this.resume = resume;
		this.status = STATUS_SUBMITTED;
		this.archived = ARCHIVED_FALSE;
		this.link = "http://localhost:8080/RestfulJobService/foundIT/jobapplication/" + this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.link = "http://localhost:8080/RestfulJobService/foundIT/jobapplication/" + this.id;
	}

	public String getJobPostId() {
		return jobPostId;
	}

	public void setJobPostId(String jobpostId) {
		this.jobPostId = jobpostId;
	}

	public String getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(String userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getJobPostLink() {
		this.jobPostLink = "http://localhost:8080/RestfulJobService/foundIT/jobposting/" + this.jobPostId;
		return this.jobPostLink;	
	}

	public void setJobPostLink(String jobPostLink) {
		this.jobPostLink = jobPostLink;
	}

	public String getUserProfileLink() {
		this.userProfileLink = "http://localhost:8080/RestfulJobService/foundIT/userprofile/" + this.userProfileId;
		return userProfileLink;
	}

	public void setUserProfileLink(String userProfileLink) {
		this.userProfileLink = userProfileLink;
	}
    

}

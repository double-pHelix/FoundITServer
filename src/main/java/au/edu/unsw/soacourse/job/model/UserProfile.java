package au.edu.unsw.soacourse.job.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProfile {
	private String id;
    private String name;
    private String currentPosition;
	private String education;
    private String pastExperience;
    private String professionalSkills;
    private String link;
    
    public UserProfile() {
		super();
	}

    public UserProfile(String id) {
		super();
		this.id = id;
		this.name = "PLEASE ENTER";
		this.currentPosition = "PLEASE ENTER";
		this.education = "PLEASE ENTER";
		this.pastExperience = "PLEASE ENTER";
		this.professionalSkills = "PLEASE ENTER";
		this.link = "http://localhost:8080/RestfulJobService/foundIT/userprofile/" + this.id;
	}
    
	public UserProfile(String id, String name, String currentPosition,
			String education, String pastExperience, String professionalSkills) {
		super();
		this.id = id;
		this.name = name;
		this.currentPosition = currentPosition;
		this.education = education;
		this.pastExperience = pastExperience;
		this.professionalSkills = professionalSkills;
		this.link = "http://localhost:8080/RestfulJobService/foundIT/userprofile/" + this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.link = "http://localhost:8080/RestfulJobService/foundIT/userprofile/" + this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPastExperience() {
		return pastExperience;
	}

	public void setPastExperience(String pastExperience) {
		this.pastExperience = pastExperience;
	}

	public String getProfessionalSkills() {
		return professionalSkills;
	}

	public void setProfessionalSkills(String professionalSkills) {
		this.professionalSkills = professionalSkills;
	}
    
    
}

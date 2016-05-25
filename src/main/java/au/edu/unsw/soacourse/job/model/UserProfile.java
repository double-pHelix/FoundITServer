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
	
    public UserProfile() {
		super();
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
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

package au.edu.unsw.soacourse.job.model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TeamMemberProfile {
	private String id;
    private String username;
    private String password;
	private String professionalSkills;
    private String link;
	private String rel;
	
	public TeamMemberProfile() {
		super();
	}
	public TeamMemberProfile(String id) {
		super();
		this.id = id;
	}
	
	public TeamMemberProfile(String id, String username, String password,
			String professionalSkills) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.professionalSkills = professionalSkills;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfessionalSkills() {
		return professionalSkills;
	}

	public void setProfessionalSkills(String professionalSkills) {
		this.professionalSkills = professionalSkills;
	}

	@XmlAttribute(name = "href")
	public String getLink() {
		this.link = "http://localhost:8080/RestfulJobService/teammemberprofile/" + this.id;
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
	
}

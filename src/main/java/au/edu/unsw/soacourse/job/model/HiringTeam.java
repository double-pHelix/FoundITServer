package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

//for transferring data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HiringTeam {
	private String id;
    private String companyProfileId;
    private String companyProfile;
    private String link;
	
	@XmlElement(name = "teamMemberProfile")
	private List<TeamMemberProfile> teamMembers = new ArrayList<TeamMemberProfile>();
    

	public HiringTeam() {
		super();
	}

	public HiringTeam(String id, String companyProfileId,
			List<TeamMemberProfile> teamMembers) {
		super();
		this.id = id;
		this.companyProfileId = companyProfileId;
		this.companyProfile = "http://localhost:8080/RestfulJobService/foundIT/hiringteam/" + this.companyProfileId;
		this.teamMembers = teamMembers;
		this.link = "http://localhost:8080/RestfulJobService/foundIT/hiringteam/" + this.id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		this.link = "http://localhost:8080/RestfulJobService/foundIT/hiringteam/" + this.id;
	}
	public String getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}

	public String getCompanyProfileId() {
		return companyProfileId;
	}
	public void setCompanyProfileId(String companyProfileId) {
		this.companyProfileId = companyProfileId;
	}
	public List<TeamMemberProfile> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<TeamMemberProfile> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


	

}

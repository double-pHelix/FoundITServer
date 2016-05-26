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
		this.teamMembers = teamMembers;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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


	

}

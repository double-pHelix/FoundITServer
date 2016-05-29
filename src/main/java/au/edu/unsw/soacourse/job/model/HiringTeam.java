package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

//for transferring data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HiringTeam {
	private String id;
    private String companyProfileId;
    private CompanyProfile companyProfileLnk;
    private String link;
    private String rel;
    
	@XmlElement(name = "teamMemberProfile")
	private List<TeamMemberProfile> teamMembers = new ArrayList<TeamMemberProfile>();
    

	public HiringTeam() {
		super();
	}
	public HiringTeam(String id) {
		super();
		this.link = "http://localhost:8080/FoundITServer/hiringteam/" + id;
	}
	
	public HiringTeam(String id, String companyProfileId,
			List<TeamMemberProfile> teamMembers) {
		super();
		this.id = id;
		this.companyProfileId = companyProfileId;
		this.companyProfileLnk = new CompanyProfile(this.companyProfileId);
		this.teamMembers = teamMembers;
		this.link = "http://localhost:8080/FoundITServer/hiringteam/" + this.id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "companyProfile")
	public CompanyProfile getCompanyProfileLnk() {
		if(this.companyProfileId == null)
			return null;
		
		companyProfileLnk = new CompanyProfile(this.companyProfileId);
		return companyProfileLnk;
	}

	public void setCompanyProfileLnk(CompanyProfile companyProfile) {
		this.companyProfileLnk = companyProfile;
		this.companyProfileId = companyProfileLnk.getId();
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
		rel = "hiringteam";
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	

}

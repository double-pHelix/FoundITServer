package au.edu.unsw.soacourse.job.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HiringTeam {
	private String id;
    private String companyProfileId;
    private String member1;
	private String member2;
    private String member3;
    private String member4;
    private String member5;
    

    
	public HiringTeam() {
		super();
	}

	public HiringTeam(String id, String companyProfileId, String member1,
			String member2, String member3, String member4, String member5) {
		super();
		this.id = id;
		this.companyProfileId = companyProfileId;
		this.member1 = member1;
		this.member2 = member2;
		this.member3 = member3;
		this.member4 = member4;
		this.member5 = member5;
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
	public String getMember1() {
		return member1;
	}
	public void setMember1(String member1) {
		this.member1 = member1;
	}
	public String getMember2() {
		return member2;
	}
	public void setMember2(String member2) {
		this.member2 = member2;
	}
	public String getMember3() {
		return member3;
	}
	public void setMember3(String member3) {
		this.member3 = member3;
	}
	public String getMember4() {
		return member4;
	}
	public void setMember4(String member4) {
		this.member4 = member4;
	}
	public String getMember5() {
		return member5;
	}
	public void setMember5(String member5) {
		this.member5 = member5;
	}

	

}

package au.edu.unsw.soacourse.job.model;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Reviews {
	@XmlElement(name = "Review")
	List<Review> reviews = new ArrayList<Review>();

	public Reviews() {
		
	}
	
	public Reviews(List<Review> reviews) {
		super();
		this.reviews = reviews;
	}

	public List<Review> getReviews() {
		return reviews;
	}
	
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	   
   
}  
package domainEntities;

public class Professor {
	private String Name;
	private String Course;
	private String Hearing_score;
	private String Interest_score;
	private String Understanding_score;
	private String Usefull_score;
	
	public Professor(String n,String c,String h,String i,String un,String us)
		{
		setName(n);
		setCourse(c);
		setHearing_score(h);
		setInterest_score(i);
		setUnderstanding_score(un);
		setUsefull_score(us);
		}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCourse() {
		return Course;
	}

	public void setCourse(String course) {
		Course = course;
	}

	public String getHearing_score() {
		return Hearing_score;
	}

	public void setHearing_score(String hearing_score) {
		Hearing_score = hearing_score;
	}

	public String getInterest_score() {
		return Interest_score;
	}

	public void setInterest_score(String interest_score) {
		Interest_score = interest_score;
	}

	public String getUnderstanding_score() {
		return Understanding_score;
	}

	public void setUnderstanding_score(String understanding_score) {
		Understanding_score = understanding_score;
	}

	public String getUsefull_score() {
		return Usefull_score;
	}

	public void setUsefull_score(String usefull_score) {
		Usefull_score = usefull_score;
	}

	@Override
	public String toString() {
		return "Professor [Name=" + Name + ", Course=" + Course
				+ ", Hearing_score=" + Hearing_score + ", Interest_score="
				+ Interest_score + ", Understanding_score="
				+ Understanding_score + ", Usefull_score=" + Usefull_score
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Course == null) ? 0 : Course.hashCode());
		result = prime * result
				+ ((Hearing_score == null) ? 0 : Hearing_score.hashCode());
		result = prime * result
				+ ((Interest_score == null) ? 0 : Interest_score.hashCode());
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime
				* result
				+ ((Understanding_score == null) ? 0 : Understanding_score
						.hashCode());
		result = prime * result
				+ ((Usefull_score == null) ? 0 : Usefull_score.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		if (Course == null) {
			if (other.Course != null)
				return false;
		} else if (!Course.equals(other.Course))
			return false;
		if (Hearing_score == null) {
			if (other.Hearing_score != null)
				return false;
		} else if (!Hearing_score.equals(other.Hearing_score))
			return false;
		if (Interest_score == null) {
			if (other.Interest_score != null)
				return false;
		} else if (!Interest_score.equals(other.Interest_score))
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Understanding_score == null) {
			if (other.Understanding_score != null)
				return false;
		} else if (!Understanding_score.equals(other.Understanding_score))
			return false;
		if (Usefull_score == null) {
			if (other.Usefull_score != null)
				return false;
		} else if (!Usefull_score.equals(other.Usefull_score))
			return false;
		return true;
	}
	
}

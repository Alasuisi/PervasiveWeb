package domainEntities;

public class Classroom {
	
	private String className;
	private int seatsOfclass;
	private int seatsTaken;
	private String assignedBeacon;
	private long classTemp;
	private long classNoise;
	private String actualCourse;
	
	
	public Classroom(){};
	
	/*public Classroom(String className, int seatsOfclass, int seatsTaken, String assignedBeacon, float classTemp,
			float classNoise, String actualCourse) {
		super();
		this.className = className;
		this.seatsOfclass = seatsOfclass;
		this.seatsTaken = seatsTaken;
		this.assignedBeacon = assignedBeacon;
		this.classTemp = classTemp;
		this.classNoise = classNoise;
		this.actualCourse = actualCourse;
	}*/
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getSeatsOfclass() {
		return seatsOfclass;
	}
	public void setSeatsOfclass(int seatsOfclass) {
		this.seatsOfclass = seatsOfclass;
	}
	public int getSeatsTaken() {
		return seatsTaken;
	}
	public void setSeatsTaken(int seatsTaken) {
		this.seatsTaken = seatsTaken;
	}
	public String getAssignedBeacon() {
		return assignedBeacon;
	}
	public void setAssignedBeacon(String assignedBeacon) {
		this.assignedBeacon = assignedBeacon;
	}
	public long getClassTemp() {
		return classTemp;
	}
	public void setClassTemp(long classTemp) {
		this.classTemp = classTemp;
	}
	public long getClassNoise() {
		return classNoise;
	}
	public void setClassNoise(long classNoise) {
		this.classNoise = classNoise;
	}
	public String getActualCourse() {
		return actualCourse;
	}
	public void setActualCourse(String actualCourse) {
		this.actualCourse = actualCourse;
	}
	
	@Override
	public String toString() {
		return "Classroom [className=" + className + ", seatsOfclass=" + seatsOfclass + ", seatsTaken=" + seatsTaken
				+ ", assignedBeacon=" + assignedBeacon + ", classTemp=" + classTemp + ", classNoise=" + classNoise
				+ ", actualCourse=" + actualCourse + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualCourse == null) ? 0 : actualCourse.hashCode());
		result = prime * result + ((assignedBeacon == null) ? 0 : assignedBeacon.hashCode());
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + Float.floatToIntBits(classNoise);
		result = prime * result + Float.floatToIntBits(classTemp);
		result = prime * result + seatsOfclass;
		result = prime * result + seatsTaken;
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
		Classroom other = (Classroom) obj;
		if (actualCourse == null) {
			if (other.actualCourse != null)
				return false;
		} else if (!actualCourse.equals(other.actualCourse))
			return false;
		if (assignedBeacon == null) {
			if (other.assignedBeacon != null)
				return false;
		} else if (!assignedBeacon.equals(other.assignedBeacon))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (Float.floatToIntBits(classNoise) != Float.floatToIntBits(other.classNoise))
			return false;
		if (Float.floatToIntBits(classTemp) != Float.floatToIntBits(other.classTemp))
			return false;
		if (seatsOfclass != other.seatsOfclass)
			return false;
		if (seatsTaken != other.seatsTaken)
			return false;
		return true;
	}
}

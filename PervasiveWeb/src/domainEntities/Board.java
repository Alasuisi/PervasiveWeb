package domainEntities;

import java.util.Date;
import java.util.LinkedList;

public class Board {
	private String objectId;
	private Course boardOfCourse;
	private LinkedList<Message> courseMessages;
	private Date creationTime;
	
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public Course getBoardOfCourse() {
		return boardOfCourse;
	}
	public void setBoardOfCourse(Course boardOfCourse) {
		this.boardOfCourse = boardOfCourse;
	}
	public LinkedList<Message> getCourseMessages() {
		return courseMessages;
	}
	public void setCourseMessages(LinkedList<Message> courseMessages) {
		this.courseMessages = courseMessages;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	private Date lastUpdate;

	@Override
	public String toString() {
		return "Board [objectId=" + objectId + ", courseMessages=" + courseMessages + ", creationTime=" + creationTime
				+ ", lastUpdate=" + lastUpdate + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseMessages == null) ? 0 : courseMessages.hashCode());
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
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
		Board other = (Board) obj;
		if (courseMessages == null) {
			if (other.courseMessages != null)
				return false;
		} else if (!courseMessages.equals(other.courseMessages))
			return false;
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		return true;
	}
	
}

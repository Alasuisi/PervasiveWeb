package domainEntities;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

public class Lecture {
		
		private String objectId;
		private String title;
		private String topics;
		private String from;
		private String to;
		private Professor prof;
		private String dayOfTheWeek;
		private Classroom classroom;
		private String topicsList;
		
		public Lecture(){}

		public Lecture(String objectId, String title, String topics,
				String from, String to, Professor prof, String dayOfTheWeek,
				Classroom classroom, String topicsList) {
			super();
			this.objectId = objectId;
			this.title = title;
			this.topics = topics;
			this.from = from;
			this.to = to;
			this.prof = prof;
			this.dayOfTheWeek = dayOfTheWeek;
			this.classroom = classroom;
			this.topicsList = topicsList;
		}

		public String getObjectId() {
			return objectId;
		}

		public void setObjectId(String objectId) {
			this.objectId = objectId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTopics() {
			return topics;
		}

		public void setTopics(String topics) {
			this.topics = topics;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public Professor getProf() {
			return prof;
		}

		public void setProf(Professor profName) {
			this.prof = profName;
		}

		public String getDayOfTheWeek() {
			return dayOfTheWeek;
		}

		public void setDayOfTheWeek(String dayOfTheWeek) {
			this.dayOfTheWeek = dayOfTheWeek;
		}

		public Classroom getClassroom() {
			return classroom;
		}

		public void setClassroom(Classroom className) {
			this.classroom = className;
		}

		public String getTopicsList() {
			return topicsList;
		}

		public void setTopicsList(String topicsList) {
			this.topicsList = topicsList;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((classroom == null) ? 0 : classroom.hashCode());
			result = prime * result + ((dayOfTheWeek == null) ? 0 : dayOfTheWeek.hashCode());
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
			result = prime * result + ((prof == null) ? 0 : prof.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			result = prime * result + ((topics == null) ? 0 : topics.hashCode());
			result = prime * result + ((topicsList == null) ? 0 : topicsList.hashCode());
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
			Lecture other = (Lecture) obj;
			if (classroom == null) {
				if (other.classroom != null)
					return false;
			} else if (!classroom.equals(other.classroom))
				return false;
			if (dayOfTheWeek == null) {
				if (other.dayOfTheWeek != null)
					return false;
			} else if (!dayOfTheWeek.equals(other.dayOfTheWeek))
				return false;
			if (from == null) {
				if (other.from != null)
					return false;
			} else if (!from.equals(other.from))
				return false;
			if (objectId == null) {
				if (other.objectId != null)
					return false;
			} else if (!objectId.equals(other.objectId))
				return false;
			if (prof == null) {
				if (other.prof != null)
					return false;
			} else if (!prof.equals(other.prof))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			if (to == null) {
				if (other.to != null)
					return false;
			} else if (!to.equals(other.to))
				return false;
			if (topics == null) {
				if (other.topics != null)
					return false;
			} else if (!topics.equals(other.topics))
				return false;
			if (topicsList == null) {
				if (other.topicsList != null)
					return false;
			} else if (!topicsList.equals(other.topicsList))
				return false;
			return true;
		}

		
		
		
		
		
		

		
		
}

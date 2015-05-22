package domainEntities;

import java.util.Date;

public class Lecture {

		private String title;
		private String topics;
		private String from;
		private String to;
		private String prof;
		private String dayOfTheWeek;
		
		public Lecture(){};
		public Lecture(String prof,String title,String topics,String from,String to,String day)
			{
			this.setTitle(title);
			this.setTopics(topics);
			this.setFrom(from);
			this.setTo(to);
			this.setProf(prof);
			this.setDayOfTheWeek(day);
			}

		public String getDayOfTheWeek() {
			return dayOfTheWeek;
		}
		public void setDayOfTheWeek(String dayOfTheWeek) {
			this.dayOfTheWeek = dayOfTheWeek;
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

		public void setFrom(String from2) {
			this.from = from2;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to2) {
			this.to = to2;
		}

		public String getProf() {
			return prof;
		}

		public void setProf(String prof2) {
			this.prof = prof2;
		}
		@Override
		public String toString() {
			return "Lecture [title=" + title + ", topics=" + topics + ", from="
					+ from + ", to=" + to + ", prof=" + prof
					+ ", dayOfTheWeek=" + dayOfTheWeek + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((dayOfTheWeek == null) ? 0 : dayOfTheWeek.hashCode());
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((prof == null) ? 0 : prof.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			result = prime * result
					+ ((topics == null) ? 0 : topics.hashCode());
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
			return true;
		}

		

		
		
}

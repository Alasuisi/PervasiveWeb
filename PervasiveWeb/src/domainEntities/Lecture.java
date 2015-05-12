package domainEntities;

import java.util.Date;

public class Lecture {

		private String attending;
	
		private String title;
		private String topics;
		private String from;
		private String to;
		
		public Lecture(String attending,String title,String topics,String from,String to)
			{
			this.setAttending(attending);
			this.setTitle(title);
			this.setTopics(topics);
			this.setFrom(from);
			this.setTo(to);
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
		/*public boolean equals(Object o)
			{
				if(!o.getClass().equals(this.getClass()))return false;
				else
					{
					 Lecture obj = (Lecture)o;
					 if(obj.getTitle().equals(this.getTitle())&&obj.getTopics().equals(this.getTopics())&&obj.getFrom().equals(this.getFrom())&&obj.getTo().equals(this.getTo())) return true;
					} 
				return false;
			}*/

		public String getAttending() {
			return attending;
		}

		public void setAttending(String attending) {
			this.attending = attending;
		}
}

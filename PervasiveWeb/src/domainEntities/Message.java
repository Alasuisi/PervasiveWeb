package domainEntities;

import java.util.Date;

public class Message {
	

	private String objectId;
	private User userMessage;
	private String message;
	private Date messageTstamp;
	private Date messageUpdatedTstamp;
	
	private Message(){}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public User getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(User userMessage) {
		this.userMessage = userMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageTstamp() {
		return messageTstamp;
	}

	public void setMessageTstamp(Date messageTstamp) {
		this.messageTstamp = messageTstamp;
	}

	public Date getMessageUpdatedTstamp() {
		return messageUpdatedTstamp;
	}

	public void setMessageUpdatedTstamp(Date messageUpdatedTstamp) {
		this.messageUpdatedTstamp = messageUpdatedTstamp;
	};
	@Override
	public String toString() {
		return "Message [objectId=" + objectId + ", userMessage=" + userMessage + ", message=" + message
				+ ", messageTstamp=" + messageTstamp + ", messageUpdatedTstamp=" + messageUpdatedTstamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((messageTstamp == null) ? 0 : messageTstamp.hashCode());
		result = prime * result + ((messageUpdatedTstamp == null) ? 0 : messageUpdatedTstamp.hashCode());
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result + ((userMessage == null) ? 0 : userMessage.hashCode());
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
		Message other = (Message) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (messageTstamp == null) {
			if (other.messageTstamp != null)
				return false;
		} else if (!messageTstamp.equals(other.messageTstamp))
			return false;
		if (messageUpdatedTstamp == null) {
			if (other.messageUpdatedTstamp != null)
				return false;
		} else if (!messageUpdatedTstamp.equals(other.messageUpdatedTstamp))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (userMessage == null) {
			if (other.userMessage != null)
				return false;
		} else if (!userMessage.equals(other.userMessage))
			return false;
		return true;
	}
}

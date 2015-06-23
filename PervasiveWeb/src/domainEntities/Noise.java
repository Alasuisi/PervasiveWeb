package domainEntities;

import java.util.LinkedList;

public class Noise {
	private long timeStamp;
	private LinkedList<Long> noiseList;
	public Noise(long timeStamp, LinkedList<Long> noiseList) {
		super();
		this.timeStamp = timeStamp;
		this.noiseList = noiseList;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public LinkedList<Long> getNoiseList() {
		return noiseList;
	}
	public void setNoiseList(LinkedList<Long> noiseList) {
		this.noiseList = noiseList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((noiseList == null) ? 0 : noiseList.hashCode());
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
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
		Noise other = (Noise) obj;
		if (noiseList == null) {
			if (other.noiseList != null)
				return false;
		} else if (!noiseList.equals(other.noiseList))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Noise [timeStamp=" + timeStamp + ", noiseList=" + noiseList
				+ "]";
	}
	
	
}

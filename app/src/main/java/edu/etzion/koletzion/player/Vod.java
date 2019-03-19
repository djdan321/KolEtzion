package edu.etzion.koletzion.player;

public class Vod {
	private String streamName, vodName, streamId, filePath, vodId, type;
	private long creationDate, duration, fileSize;
	
	public Vod(String streamName, String vodName, String streamId, String filePath, String vodId,
	           String type, long creationDate, long duration, long fileSize) {
		this.streamName = streamName;
		this.vodName = vodName;
		this.streamId = streamId;
		this.filePath = filePath;
		this.vodId = vodId;
		this.type = type;
		this.creationDate = creationDate;
		this.duration = duration;
		this.fileSize = fileSize;
	}
	
	public String getStreamName() {
		return streamName;
	}
	
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	
	public String getVodName() {
		return vodName;
	}
	
	public void setVodName(String vodName) {
		this.vodName = vodName;
	}
	
	public String getStreamId() {
		return streamId;
	}
	
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getVodId() {
		return vodId;
	}
	
	public void setVodId(String vodId) {
		this.vodId = vodId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public long getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	@Override
	public String toString() {
		return "Vod{" +
				"streamName='" + streamName + '\'' +
				", vodName='" + vodName + '\'' +
				", streamId='" + streamId + '\'' +
				", filePath='" + filePath + '\'' +
				", vodId='" + vodId + '\'' +
				", type='" + type + '\'' +
				", creationDate=" + creationDate +
				", duration=" + duration +
				", fileSize=" + fileSize +
				'}';
	}
}

package edu.etzion.koletzion.player;

class LiveStream {
	private String name;
	private String status;
	private String streamUrl;
	
	public LiveStream(String name, String status, String streamUrl) {
		this.name = name;
		this.status = status;
		this.streamUrl = streamUrl;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getStreamUrl() {
		return streamUrl;
	}
}

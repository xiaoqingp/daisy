package pxq.daisy.test.mode.request;

public class Num2TimeRequest {
	/**
	 * 时间戳，可以是秒或者毫秒值
	 */
	private long timestampValue;
	/**
	 * 时间戳类型：1 秒；2 毫秒
	 */
	private int timestampType;
	
	
	public long getTimestampValue() {
		return timestampValue;
	}
	public void setTimestampValue(long timestampValue) {
		this.timestampValue = timestampValue;
	}
	public int getTimestampType() {
		return timestampType;
	}
	public void setTimestampType(int timestampType) {
		this.timestampType = timestampType;
	}
	
	
}

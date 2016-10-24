package care.variables;

public class LoadTestCell {
	
	private String testId;
	private String testCellId;
	private String screenName;
	
	public LoadTestCell(){
		
		this.testCellId = null;
		this.testId		= null;
		this.screenName = null;
	}
	
	public LoadTestCell(String testId, String testCellId, String screenName){
		
		this.testCellId = testCellId;
		this.testId		= testId;
		this.screenName = screenName;
	}
	
	
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	public String getTestCellId() {
		return testCellId;
	}
	public void setTestCellId(String testCellId) {
		this.testCellId = testCellId;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

}
 
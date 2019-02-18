package uy.gub.dgi.gx;

public class ServiceResult {

	private Integer errorCode = 0;
	private String errorDsc;
	private String result;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDsc() {
		return errorDsc;
	}

	public void setErrorDsc(String errorDsc) {
		this.errorDsc = errorDsc;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ServiceResult(Integer errorCode, String errorDsc, String result) {
		super();
		this.errorCode = errorCode;
		this.errorDsc = errorDsc;
		this.result = result;
	}

	public ServiceResult() {
		super();
	}

}

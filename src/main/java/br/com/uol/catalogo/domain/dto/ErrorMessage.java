package br.com.uol.catalogo.domain.dto;

public class ErrorMessage {
	
	private Integer	code;
	private String message;
	
	public ErrorMessage() {}
	
	public ErrorMessage(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

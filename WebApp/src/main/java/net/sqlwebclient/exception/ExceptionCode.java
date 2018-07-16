package net.sqlwebclient.exception;

public enum ExceptionCode {
	LANGUAGE_NOT_SUPPORTED("language_not_found"),
	SETTING_NOT_FOUND("setting_not_found"),
	EXPORT_TYPE_NOT_SUPPORTED("export_type_not_supported"),
	ENTITY_MAPPING_NOT_FOUND("entity_mapping_not_found")
	;

	private final String code;


	ExceptionCode(final String code) {
		this.code = "exception." + code;
	}

	public String getCode() {
		return code;
	}
}

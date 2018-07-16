package net.sqlwebclient.json.objects;

public final class JsonQueryRequest extends Json {
	private static final long serialVersionUID = -9219344610073650033L;

	private Long connectionUuid;
	private String queryString;
	private Integer limit;
	private Integer start;
	private String queryType;

	private JsonQueryRequest() {
		//leave empty
	}

	public Long getConnectionUuid() {
		return connectionUuid;
	}

	public String getQueryString() {
		return queryString;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getStart() {
		return start;
	}

	public String getQueryType() {
		return queryType;
	}
}

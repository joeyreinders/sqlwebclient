package com.sqlwebclient.demo;

import com.google.common.collect.ImmutableMap;
import com.mysql.jdbc.Driver;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5Dialect;

import java.util.Map;
import java.util.Properties;

public final class JarBuilder {
	public static void main(final String[] args) {
		
	}

	private org.hibernate.cfg.Configuration createConfiguration() {
		final String url = buildUrl("localhost", 3306, "employees");
		final Map<String, Object> map = ImmutableMap.<String, Object> builder()
				.put(Environment.DRIVER, Driver.class.getName())
				.put(Environment.USER, "root")
				.put(Environment.PASS, "root")
				.put(Environment.DIALECT, new MySQL5Dialect())
				.put(Environment.URL, url)
				.put(Environment.GENERATE_STATISTICS, false)
				.build();

		final Properties props = new Properties();
		props.putAll(map);

		return new org.hibernate.cfg.Configuration().setProperties(props);
	}

	private String buildUrl(final String host, final int port, final String database) {
		String url = "jdbc:mysql://" + host + ":" + port;
		if(! (database == null || database.isEmpty())) {
			url += "/" + database;
		}

		return url;
	}
}

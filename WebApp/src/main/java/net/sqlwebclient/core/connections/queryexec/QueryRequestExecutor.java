package net.sqlwebclient.core.connections.queryexec;

import net.sqlwebclient.core.objects.result.Result;

public interface QueryRequestExecutor {
	Result execute();

	void setExecutorContext(final ExecutorContext context);
}

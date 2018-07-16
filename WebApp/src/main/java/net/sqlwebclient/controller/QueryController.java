package net.sqlwebclient.controller;

import com.google.inject.Inject;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.core.objects.ConnectionDetails;
import net.sqlwebclient.core.objects.QueryRequest;
import net.sqlwebclient.json.objects.JsonQueryRequest;
import net.sqlwebclient.service.ConnectionDetailsService;
import net.sqlwebclient.service.QueryService;

@RequestMapping("/api/query")
final class QueryController extends CrudController {
    private static final long serialVersionUID = 6744316050006143007L;

    private final QueryService queryService;
    private final ConnectionDetailsService connectionDetailsService;

    @Inject
	QueryController(final QueryService queryService,
					final ConnectionDetailsService connectionDetailsService) {
        this.queryService = queryService;
        this.connectionDetailsService = connectionDetailsService;
    }

	@Override
	protected CtrlResult post(final RequestContext ctx) {
		final JsonQueryRequest jsonQueryRequest =
				getJsonConversionService().fromJson(ctx.getBody(), JsonQueryRequest.class);
		final ConnectionDetails connectionDetails = connectionDetailsService.getByUuid(jsonQueryRequest.getConnectionUuid());

		final QueryRequest request = createQueryRequest(connectionDetails, jsonQueryRequest);

		final Object res = queryService.run(request);
		return ok(res);
	}

	private QueryRequest createQueryRequest(final ConnectionDetails connectionDetails,
											final JsonQueryRequest jsonQueryRequest) {
		return QueryRequest.builder()
				.addQueryString(jsonQueryRequest.getQueryString())
				.addConnectionDetails(connectionDetails)
				.addLimit(jsonQueryRequest.getLimit())
				.addStart(jsonQueryRequest.getStart())
				.addQueryType(jsonQueryRequest.getQueryType())
				.build();
	}
}

package com.fifaminer.resource;

import com.fifaminer.service.operation.OperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static com.fifaminer.resource.UrlPath.*;

@Component
@Path(OPERATIONS)
public class OperationsResource {

    private final OperationsService operationsService;

    @Autowired
    public OperationsResource(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @POST
    @Path(TRANSACTION_ANALYSE)
    public Response runTransactionAnalyse() {
        operationsService.runTransactionAnalyse();
        return Response.accepted().build();
    }
}

package si.fri.prpo.api.v1.mappers;

import si.fri.prpo.izjeme.NeveljavenIzdelekIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenIzdelekIzjemaExceptionMapper implements ExceptionMapper<NeveljavenIzdelekIzjema> {

    @Override
    public Response toResponse(NeveljavenIzdelekIzjema neveljavenIzdelekIzjema) {
        return Response
                .status(Response.Status.NOT_ACCEPTABLE)
                .entity("{" + neveljavenIzdelekIzjema.getMessage() + "}")
                .build();
    }
}

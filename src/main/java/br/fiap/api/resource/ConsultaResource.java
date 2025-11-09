package br.fiap.api.resource;

import br.fiap.api.model.Consulta;
import br.fiap.api.service.ConsultaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    @Inject ConsultaService service;

    @GET
    public List<Consulta> list(@QueryParam("limit") @DefaultValue("20") int limit,
                               @QueryParam("offset") @DefaultValue("0") int offset) throws Exception {
        return service.list(limit, offset);
    }

    @GET @Path("/{id}")
    public Response get(@PathParam("id") String id) throws Exception {
        Consulta c = service.get(id);
        return c == null ? Response.status(404).build() : Response.ok(c).build();
    }

    @POST
    public Response create(Consulta c) throws Exception {
        String id = service.create(c);
        return Response.status(201).entity(service.get(id)).build();
    }

    @PUT @Path("/{id}")
    public Response update(@PathParam("id") String id, Consulta c) throws Exception {
        return service.update(id, c) ? Response.ok(service.get(id)).build() : Response.status(404).build();
    }

    @DELETE @Path("/{id}")
    public Response delete(@PathParam("id") String id) throws Exception {
        return service.delete(id) ? Response.noContent().build() : Response.status(404).build();
    }
}

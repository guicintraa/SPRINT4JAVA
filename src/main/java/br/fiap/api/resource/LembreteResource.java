package br.fiap.api.resource;

import br.fiap.api.dao.LembreteDao;
import br.fiap.api.model.Lembrete;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/lembretes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LembreteResource {

    @Inject
    LembreteDao dao;

    // GET /lembretes?consulta=C0001
    @GET
    public Response listByConsulta(@QueryParam("consulta") String idConsulta) {
        if (idConsulta == null || idConsulta.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"parametro 'consulta' é obrigatório\"}")
                    .build();
        }
        try {
            List<Lembrete> lista = dao.findByConsulta(idConsulta);
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.serverError().entity(sqlError(e)).build();
        }
    }

    // GET /lembretes/{id}
    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        try {
            Lembrete l = dao.findById(id);
            if (l == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(l).build();
        } catch (SQLException e) {
            return Response.serverError().entity(sqlError(e)).build();
        }
    }

    // POST /lembretes   (JSON pode vir em snake_case por causa da config SNAKE_CASE)
    @POST
    public Response create(Lembrete body) {
        try {
            Long id = dao.create(body);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"id\":" + id + "}")
                    .build();
        } catch (SQLException e) {
            // FK quebrada, checks, etc.
            return Response.status(Response.Status.BAD_REQUEST).entity(sqlError(e)).build();
        }
    }

    // PUT /lembretes/{id}
    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, Lembrete body) {
        try {
            boolean ok = dao.update(id, body);
            if (!ok) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(sqlError(e)).build();
        }
    }

    // DELETE /lembretes/{id}
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            boolean ok = dao.delete(id);
            if (!ok) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(sqlError(e)).build();
        }
    }

    private String sqlError(SQLException e) {
        String msg = e.getMessage();
        // Mensagens mais legíveis p/ professor
        if (msg != null && msg.contains("ORA-02291")) {
            return "{\"error\":\"Violação de FK: id_consulta não existe em T_CONSULTA\"}";
        }
        if (msg != null && msg.contains("ORA-01400")) {
            return "{\"error\":\"Campo obrigatório está nulo (verifique tp_lembrete, canal_envio, status_envio, id_consulta)\"}";
        }
        if (msg != null && msg.contains("ORA-02289")) {
            return "{\"error\":\"Sequência não existe. Garanta RM562850.SQ_T_LEMBRETES\"}";
        }
        return "{\"error\":\"SQL: " + msg.replace("\"", "\\\"") + "\"}";
    }
}

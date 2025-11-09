package br.fiap.api.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("/health")
public class HealthResource {

  @Inject
  DataSource ds;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String health() {
    try (Connection c = ds.getConnection();
         PreparedStatement ps = c.prepareStatement(
                 "SELECT sys_context('USERENV','CURRENT_SCHEMA') FROM dual");
         ResultSet rs = ps.executeQuery()) {
      rs.next();
      String schema = rs.getString(1);
      return "{\"ok\":true,\"schema\":\"" + schema + "\"}";
    } catch (Exception e) {
      return "{\"ok\":false,\"error\":\"" + e.getMessage().replace("\"","\\\"") + "\"}";
    }
  }
}

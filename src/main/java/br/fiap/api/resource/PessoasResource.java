package br.fiap.api.resource;

import br.fiap.api.dao.MedicoDao;
import br.fiap.api.dao.PacienteDao;
import br.fiap.api.model.Medico;
import br.fiap.api.model.Paciente;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
public class PessoasResource {

    @Inject PacienteDao pacienteDao;
    @Inject MedicoDao medicoDao;

    @GET @Path("/pacientes")
    public List<Paciente> pacientes() throws Exception { return pacienteDao.listar(); }

    @GET @Path("/medicos")
    public List<Medico> medicos() throws Exception { return medicoDao.listar(); }
}

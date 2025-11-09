package br.fiap.api.service;

import br.fiap.api.dao.RespostaPacienteDao;
import br.fiap.api.model.RespostaPaciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class RespostaPacienteService {
    @Inject RespostaPacienteDao dao;
    public List<RespostaPaciente> byLembrete(Long id) throws SQLException { return dao.findByLembrete(id); }
    public Long create(RespostaPaciente r) throws SQLException { return dao.create(r); }
}

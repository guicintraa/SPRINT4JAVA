package br.fiap.api.service;

import br.fiap.api.dao.ConsultaDao;
import br.fiap.api.model.Consulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ConsultaService {
  @Inject ConsultaDao dao;
  public List<Consulta> list(int limit, int offset) throws SQLException { return dao.findAll(limit, offset); }
  public Consulta get(String id) throws SQLException { return dao.findById(id); }
  public String create(Consulta c) throws SQLException { return dao.create(c); }
  public boolean update(String id, Consulta c) throws SQLException { return dao.update(id, c); }
  public boolean delete(String id) throws SQLException { return dao.delete(id); }
}

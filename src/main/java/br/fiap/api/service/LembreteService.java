package br.fiap.api.service;

import br.fiap.api.dao.LembreteDao;
import br.fiap.api.model.Lembrete;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class LembreteService {
  @Inject LembreteDao dao;
  public List<Lembrete> byConsulta(String idConsulta) throws SQLException { return dao.findByConsulta(idConsulta); }
  public Long create(Lembrete l) throws SQLException { return dao.create(l); }
  public boolean update(Long id, Lembrete l) throws SQLException { return dao.update(id, l); }
  public boolean delete(Long id) throws SQLException { return dao.delete(id); }
}

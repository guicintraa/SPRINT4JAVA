package br.fiap.api.dao;

import br.fiap.api.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@ApplicationScoped
public class PacienteDao {
    @Inject DataSource ds;

    public List<Paciente> listar() throws SQLException {
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT id_paciente, nm_paciente FROM t_paciente ORDER BY id_paciente");
             ResultSet rs = ps.executeQuery()) {
            List<Paciente> out = new ArrayList<>();
            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt(1));
                p.setNmPaciente(rs.getString(2));
                out.add(p);
            }
            return out;
        }
    }
}

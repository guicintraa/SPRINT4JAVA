package br.fiap.api.dao;

import br.fiap.api.model.Medico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@ApplicationScoped
public class MedicoDao {
    @Inject DataSource ds;

    public List<Medico> listar() throws SQLException {
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT id_medico, nm_medico FROM t_medico ORDER BY id_medico");
             ResultSet rs = ps.executeQuery()) {
            List<Medico> out = new ArrayList<>();
            while (rs.next()) {
                Medico m = new Medico();
                m.setIdMedico(rs.getInt(1));
                m.setNmMedico(rs.getString(2));
                out.add(m);
            }
            return out;
        }
    }
}

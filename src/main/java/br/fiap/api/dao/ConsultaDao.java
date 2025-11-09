package br.fiap.api.dao;

import br.fiap.api.model.Consulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaDao {

    @Inject DataSource ds;

    private Consulta map(ResultSet rs) throws SQLException {
        Consulta c = new Consulta();
        c.setIdConsulta(rs.getString("id_consulta"));
        c.setIdPaciente(rs.getInt("id_paciente"));
        c.setIdMedico(rs.getInt("id_medico"));
        Timestamp ts = rs.getTimestamp("dt_consulta");
        c.setDtConsulta(ts != null ? ts.toLocalDateTime() : null);
        return c;
    }

    public List<Consulta> findAll(int limit, int offset) throws SQLException {
        String sql = """
            SELECT id_consulta,id_paciente,id_medico,dt_consulta
              FROM t_consulta
             ORDER BY dt_consulta DESC
             OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        """;
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                List<Consulta> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    public Consulta findById(String id) throws SQLException {
        String sql = "SELECT id_consulta,id_paciente,id_medico,dt_consulta FROM t_consulta WHERE id_consulta=?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public String create(Consulta body) throws SQLException {
        // gera id: C0001 ...
        String nextIdSql = "SELECT 'C'||LPAD(SQ_CONSULTA.NEXTVAL,4,'0') AS id FROM dual";
        String newId;
        try (Connection c = ds.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(nextIdSql)) {
            rs.next();
            newId = rs.getString("id");
        }
        String insert = "INSERT INTO t_consulta (id_consulta,id_paciente,id_medico,dt_consulta) VALUES (?,?,?,?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(insert)) {
            ps.setString(1, newId);
            ps.setInt(2, body.getIdPaciente());
            ps.setInt(3, body.getIdMedico());
            ps.setTimestamp(4, Timestamp.valueOf(body.getDtConsulta()));
            ps.executeUpdate();
        }
        return newId;
    }

    public boolean update(String id, Consulta body) throws SQLException {
        String sql = "UPDATE t_consulta SET id_paciente=?, id_medico=?, dt_consulta=? WHERE id_consulta=?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, body.getIdPaciente());
            ps.setInt(2, body.getIdMedico());
            ps.setTimestamp(3, body.getDtConsulta() != null ? Timestamp.valueOf(body.getDtConsulta()) : null);
            ps.setString(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(String id) throws SQLException {
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM t_consulta WHERE id_consulta=?")) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

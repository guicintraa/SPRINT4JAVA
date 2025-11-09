package br.fiap.api.dao;

import br.fiap.api.model.Lembrete;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LembreteDao {

    @Inject
    DataSource ds;

    private Lembrete map(ResultSet rs) throws SQLException {
        Lembrete l = new Lembrete();
        l.setIdLembrete(rs.getLong("id_lembrete"));
        l.setTpLembrete(rs.getString("tp_lembrete"));
        l.setCanalEnvio(rs.getString("canal_envio"));
        l.setStatusEnvio(rs.getString("status_envio"));
        l.setIdConsulta(rs.getString("id_consulta"));
        return l;
    }

    public List<Lembrete> findByConsulta(String idConsulta) throws SQLException {
        final String sql = """
            SELECT id_lembrete, tp_lembrete, canal_envio, status_envio, id_consulta
              FROM RM562850.T_LEMBRETES
             WHERE id_consulta = ?
             ORDER BY id_lembrete
        """;
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                List<Lembrete> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
            
        }
    }

    public Long create(Lembrete body) throws SQLException {
        // Defaults para evitar ORA-01400 quando o JSON vier incompleto
        String tp   = body.getTpLembrete();
        String can  = body.getCanalEnvio();
        String stat = body.getStatusEnvio();
        String idc  = body.getIdConsulta();

        if (tp == null   || tp.isBlank())   tp   = "MANUAL";    // T-24H | T-2H | MANUAL
        if (can == null  || can.isBlank())  can  = "SMS";       // SMS | EMAIL | WHATSAPP
        if (stat == null || stat.isBlank()) stat = "PENDENTE";  // ENVIADO | FALHA | PENDENTE

        if (idc == null || idc.isBlank()) {
            throw new SQLException("id_consulta é obrigatório");
        }

        long id;

        // 1) Pega o próximo valor da sequência CERTA (qualificada no seu schema)
        try (Connection c = ds.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT RM562850.SQ_T_LEMBRETES.NEXTVAL AS id FROM dual")) {
            rs.next();
            id = rs.getLong("id");
        }

        // 2) Insere o registro (tabela qualificada para evitar problemas de schema)
        final String insert = """
            INSERT INTO RM562850.T_LEMBRETES
              (id_lembrete, tp_lembrete, canal_envio, status_envio, id_consulta)
            VALUES
              (?, ?, ?, ?, ?)
        """;

        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(insert)) {
            ps.setLong(1, id);
            ps.setString(2, tp);
            ps.setString(3, can);
            ps.setString(4, stat);
            ps.setString(5, idc); // precisa existir em T_CONSULTA (FK)
            ps.executeUpdate();
        }

        return id;
    }

    public boolean update(Long id, Lembrete body) throws SQLException {
        // Se quiser manter defaults também no update (opcional)
        String tp   = body.getTpLembrete();
        String can  = body.getCanalEnvio();
        String stat = body.getStatusEnvio();
        String idc  = body.getIdConsulta();

        if (tp == null   || tp.isBlank())   tp   = "MANUAL";
        if (can == null  || can.isBlank())  can  = "SMS";
        if (stat == null || stat.isBlank()) stat = "PENDENTE";

        final String sql = """
            UPDATE RM562850.T_LEMBRETES
               SET tp_lembrete = ?, canal_envio = ?, status_envio = ?, id_consulta = ?
             WHERE id_lembrete = ?
        """;
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tp);
            ps.setString(2, can);
            ps.setString(3, stat);
            ps.setString(4, idc);
            ps.setLong(5, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(Long id) throws SQLException {
        final String sql = "DELETE FROM RM562850.T_LEMBRETES WHERE id_lembrete = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }

    }

    public Lembrete findById(Long id) throws SQLException {
        final String sql = """
        SELECT id_lembrete, tp_lembrete, canal_envio, status_envio, id_consulta
          FROM RM562850.T_LEMBRETES
         WHERE id_lembrete = ?
    """;
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        }
    }
    {
    }
}

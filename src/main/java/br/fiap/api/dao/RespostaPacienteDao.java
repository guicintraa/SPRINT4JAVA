package br.fiap.api.dao;

import br.fiap.api.model.RespostaPaciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RespostaPacienteDao {

    @Inject DataSource ds;

    private RespostaPaciente map(ResultSet rs) throws SQLException {
        RespostaPaciente r = new RespostaPaciente();
        r.setIdResposta(rs.getLong("id_resposta"));
        r.setRespostaTexto(rs.getString("resposta_texto"));
        Timestamp ts = rs.getTimestamp("dt_resposta");
        r.setDtResposta(ts != null ? ts.toLocalDateTime() : null);
        r.setConclusaoChecagem(rs.getString("conclusao_checagem"));
        r.setIdLembrete(rs.getLong("t_lembretes_id_lembrete"));
        return r;
    }

    public List<RespostaPaciente> findByLembrete(Long idLembrete) throws SQLException {
        String sql = """
            SELECT id_resposta,resposta_texto,dt_resposta,conclusao_checagem,t_lembretes_id_lembrete
              FROM t_resposta_paciente
             WHERE t_lembretes_id_lembrete=?
             ORDER BY id_resposta
        """;
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, idLembrete);
            try (ResultSet rs = ps.executeQuery()) {
                List<RespostaPaciente> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    public Long create(RespostaPaciente body) throws SQLException {
        long id;
        try (Connection c = ds.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT SQ_RESPOSTA_PAC.NEXTVAL AS id FROM dual")) {
            rs.next();
            id = rs.getLong("id");
        }
        String insert = """
          INSERT INTO t_resposta_paciente
            (id_resposta,resposta_texto,dt_resposta,conclusao_checagem,t_lembretes_id_lembrete)
           VALUES (?,?,?,?,?)
        """;
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(insert)) {
            ps.setLong(1, id);
            ps.setString(2, body.getRespostaTexto());
            ps.setTimestamp(3, Timestamp.valueOf(body.getDtResposta() != null ? body.getDtResposta() : LocalDateTime.now()));
            ps.setString(4, body.getConclusaoChecagem());
            ps.setLong(5, body.getIdLembrete());
            ps.executeUpdate();
        }
        return id;
    }
}

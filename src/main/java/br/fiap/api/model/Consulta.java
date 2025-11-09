package br.fiap.api.model;

import java.time.LocalDateTime;

public class Consulta {
  private String idConsulta;      // C0001...
  private Integer idPaciente;
  private Integer idMedico;
  private LocalDateTime dtConsulta;

  public String getIdConsulta() { return idConsulta; }
  public void setIdConsulta(String idConsulta) { this.idConsulta = idConsulta; }
  public Integer getIdPaciente() { return idPaciente; }
  public void setIdPaciente(Integer idPaciente) { this.idPaciente = idPaciente; }
  public Integer getIdMedico() { return idMedico; }
  public void setIdMedico(Integer idMedico) { this.idMedico = idMedico; }
  public LocalDateTime getDtConsulta() { return dtConsulta; }
  public void setDtConsulta(LocalDateTime dtConsulta) { this.dtConsulta = dtConsulta; }

  public static class RespostaPaciente {
      private Long idResposta;            // sequence (se existir)
      private String respostaTexto;       // OK, CONFIRMADO, NAO, ...
      private LocalDateTime dtResposta;
      private String conclusaoChecagem;   // OK, PROBLEMA, NAO_RESPONDEU...
      private Long idLembrete;            // FK -> t_lembretes.id_lembrete

      public Long getIdResposta() { return idResposta; }
      public void setIdResposta(Long idResposta) { this.idResposta = idResposta; }
      public String getRespostaTexto() { return respostaTexto; }
      public void setRespostaTexto(String respostaTexto) { this.respostaTexto = respostaTexto; }
      public LocalDateTime getDtResposta() { return dtResposta; }
      public void setDtResposta(LocalDateTime dtResposta) { this.dtResposta = dtResposta; }
      public String getConclusaoChecagem() { return conclusaoChecagem; }
      public void setConclusaoChecagem(String conclusaoChecagem) { this.conclusaoChecagem = conclusaoChecagem; }
      public Long getIdLembrete() { return idLembrete; }
      public void setIdLembrete(Long idLembrete) { this.idLembrete = idLembrete; }
  }
}

package br.fiap.api.model;

import java.time.LocalDateTime;

public class RespostaPaciente {
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

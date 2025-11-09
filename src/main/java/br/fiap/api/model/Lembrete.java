package br.fiap.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lembrete {

  private Long idLembrete;

  @JsonProperty("tp_lembrete")
  private String tpLembrete;

  @JsonProperty("canal_envio")
  private String canalEnvio;

  @JsonProperty("status_envio")
  private String statusEnvio;

  @JsonProperty("id_consulta")
  private String idConsulta;

  // Getters e Setters
  public Long getIdLembrete() {
    return idLembrete;
  }

  public void setIdLembrete(Long idLembrete) {
    this.idLembrete = idLembrete;
  }

  public String getTpLembrete() {
    return tpLembrete;
  }

  public void setTpLembrete(String tpLembrete) {
    this.tpLembrete = tpLembrete;
  }

  public String getCanalEnvio() {
    return canalEnvio;
  }

  public void setCanalEnvio(String canalEnvio) {
    this.canalEnvio = canalEnvio;
  }

  public String getStatusEnvio() {
    return statusEnvio;
  }

  public void setStatusEnvio(String statusEnvio) {
    this.statusEnvio = statusEnvio;
  }

  public String getIdConsulta() {
    return idConsulta;
  }

  public void setIdConsulta(String idConsulta) {
    this.idConsulta = idConsulta;
  }
}



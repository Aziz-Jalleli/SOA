package com.pi.restaurantservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** Adresse d'un restaurant, stockée dans les colonnes de la table restaurant. */
@Embeddable
public class Adresse {

	@Column(name = "numero", nullable = false, length = 20)
	private String numero;

	@Column(name = "rue", nullable = false)
	private String rue;

	@Column(name = "cite", nullable = false)
	private String cite;

	@Column(name = "ville", nullable = false)
	private String ville;

	public Adresse() {
	}

	public Adresse(String numero, String rue, String cite, String ville) {
		this.numero = numero;
		this.rue = rue;
		this.cite = cite;
		this.ville = ville;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCite() {
		return cite;
	}

	public void setCite(String cite) {
		this.cite = cite;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
}

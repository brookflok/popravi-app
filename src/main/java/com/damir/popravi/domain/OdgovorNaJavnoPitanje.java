package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A OdgovorNaJavnoPitanje.
 */
@Entity
@Table(name = "odgovor_na_javno_pitanje")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "odgovornajavnopitanje")
public class OdgovorNaJavnoPitanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "odgovor")
    private String odgovor;

    @Column(name = "datum")
    private Instant datum;

    @Column(name = "prikaz")
    private Boolean prikaz;

    @ManyToOne
    @JsonIgnoreProperties(value = "odgovorNaJavnoPitanjes", allowSetters = true)
    private DodatniInfoUser dodatniinfoUser;

    @OneToOne(mappedBy = "odgovorNaJavnoPitanje")
    @JsonIgnore
    private JavnoPitanje javnoPitanje;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public OdgovorNaJavnoPitanje odgovor(String odgovor) {
        this.odgovor = odgovor;
        return this;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public Instant getDatum() {
        return datum;
    }

    public OdgovorNaJavnoPitanje datum(Instant datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public Boolean isPrikaz() {
        return prikaz;
    }

    public OdgovorNaJavnoPitanje prikaz(Boolean prikaz) {
        this.prikaz = prikaz;
        return this;
    }

    public void setPrikaz(Boolean prikaz) {
        this.prikaz = prikaz;
    }

    public DodatniInfoUser getDodatniinfoUser() {
        return dodatniinfoUser;
    }

    public OdgovorNaJavnoPitanje dodatniinfoUser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniinfoUser = dodatniInfoUser;
        return this;
    }

    public void setDodatniinfoUser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniinfoUser = dodatniInfoUser;
    }

    public JavnoPitanje getJavnoPitanje() {
        return javnoPitanje;
    }

    public OdgovorNaJavnoPitanje javnoPitanje(JavnoPitanje javnoPitanje) {
        this.javnoPitanje = javnoPitanje;
        return this;
    }

    public void setJavnoPitanje(JavnoPitanje javnoPitanje) {
        this.javnoPitanje = javnoPitanje;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OdgovorNaJavnoPitanje)) {
            return false;
        }
        return id != null && id.equals(((OdgovorNaJavnoPitanje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OdgovorNaJavnoPitanje{" +
            "id=" + getId() +
            ", odgovor='" + getOdgovor() + "'" +
            ", datum='" + getDatum() + "'" +
            ", prikaz='" + isPrikaz() + "'" +
            "}";
    }
}

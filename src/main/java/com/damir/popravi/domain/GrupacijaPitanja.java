package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A GrupacijaPitanja.
 */
@Entity
@Table(name = "grupacija_pitanja")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "grupacijapitanja")
public class GrupacijaPitanja implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datum")
    private Instant datum;

    @OneToMany(mappedBy = "grupacijaPitanja", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({ "grupacijaPitanja" })
    private Set<JavnoPitanje> javnoPitanjes = new HashSet<>();

    @OneToOne(mappedBy = "grupacijaPitanja")
    @JsonIgnore
    private Artikl artikl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatum() {
        return datum;
    }

    public GrupacijaPitanja datum(Instant datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public Set<JavnoPitanje> getJavnoPitanjes() {
        return javnoPitanjes;
    }

    public GrupacijaPitanja javnoPitanjes(Set<JavnoPitanje> javnoPitanjes) {
        this.javnoPitanjes = javnoPitanjes;
        return this;
    }

    public GrupacijaPitanja addJavnoPitanje(JavnoPitanje javnoPitanje) {
        this.javnoPitanjes.add(javnoPitanje);
        javnoPitanje.setGrupacijaPitanja(this);
        return this;
    }

    public GrupacijaPitanja removeJavnoPitanje(JavnoPitanje javnoPitanje) {
        this.javnoPitanjes.remove(javnoPitanje);
        javnoPitanje.setGrupacijaPitanja(null);
        return this;
    }

    public void setJavnoPitanjes(Set<JavnoPitanje> javnoPitanjes) {
        this.javnoPitanjes = javnoPitanjes;
    }

    public Artikl getArtikl() {
        return artikl;
    }

    public GrupacijaPitanja artikl(Artikl artikl) {
        this.artikl = artikl;
        return this;
    }

    public void setArtikl(Artikl artikl) {
        this.artikl = artikl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupacijaPitanja)) {
            return false;
        }
        return id != null && id.equals(((GrupacijaPitanja) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupacijaPitanja{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}

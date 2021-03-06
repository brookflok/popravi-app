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
 * A Galerija.
 */
@Entity
@Table(name = "galerija")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "galerija")
public class Galerija implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "datum")
    private Instant datum;

    @OneToMany(mappedBy = "galerija", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({ "galerija" })
    private Set<Slika> slikas = new HashSet<>();

    @OneToOne(mappedBy = "galerija")
    @JsonIgnore
    private Artikl artikl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public Galerija ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Instant getDatum() {
        return datum;
    }

    public Galerija datum(Instant datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public Set<Slika> getSlikas() {
        return slikas;
    }

    public Galerija slikas(Set<Slika> slikas) {
        this.slikas = slikas;
        return this;
    }

    public Galerija addSlika(Slika slika) {
        this.slikas.add(slika);
        slika.setGalerija(this);
        return this;
    }

    public Galerija removeSlika(Slika slika) {
        this.slikas.remove(slika);
        slika.setGalerija(null);
        return this;
    }

    public void setSlikas(Set<Slika> slikas) {
        this.slikas = slikas;
    }

    public Artikl getArtikl() {
        return artikl;
    }

    public Galerija artikl(Artikl artikl) {
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
        if (!(o instanceof Galerija)) {
            return false;
        }
        return id != null && id.equals(((Galerija) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Galerija{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}

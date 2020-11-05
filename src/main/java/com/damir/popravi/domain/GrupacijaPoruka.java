package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A GrupacijaPoruka.
 */
@Entity
@Table(name = "grupacija_poruka")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "grupacijaporuka")
public class GrupacijaPoruka implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datum")
    private Instant datum;

    @OneToMany(mappedBy = "grupacijaPoruka")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Poruka> porukas = new HashSet<>();

    @OneToOne(mappedBy = "grupacijaPoruka")
    @JsonIgnore
    private Chat chat;

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

    public GrupacijaPoruka datum(Instant datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public Set<Poruka> getPorukas() {
        return porukas;
    }

    public GrupacijaPoruka porukas(Set<Poruka> porukas) {
        this.porukas = porukas;
        return this;
    }

    public GrupacijaPoruka addPoruka(Poruka poruka) {
        this.porukas.add(poruka);
        poruka.setGrupacijaPoruka(this);
        return this;
    }

    public GrupacijaPoruka removePoruka(Poruka poruka) {
        this.porukas.remove(poruka);
        poruka.setGrupacijaPoruka(null);
        return this;
    }

    public void setPorukas(Set<Poruka> porukas) {
        this.porukas = porukas;
    }

    public Chat getChat() {
        return chat;
    }

    public GrupacijaPoruka chat(Chat chat) {
        this.chat = chat;
        return this;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupacijaPoruka)) {
            return false;
        }
        return id != null && id.equals(((GrupacijaPoruka) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupacijaPoruka{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}

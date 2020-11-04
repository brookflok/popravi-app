package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Ucesnici.
 */
@Entity
@Table(name = "ucesnici")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ucesnici")
public class Ucesnici implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datum")
    private Instant datum;

    @OneToOne
    @JoinColumn(unique = true)
    private Chat chat;

    @ManyToOne
    @JsonIgnoreProperties(value = "ucesnicis", allowSetters = true)
    private DodatniInfoUser dodatniInfoUser;

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

    public Ucesnici datum(Instant datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public Chat getChat() {
        return chat;
    }

    public Ucesnici chat(Chat chat) {
        this.chat = chat;
        return this;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public DodatniInfoUser getDodatniInfoUser() {
        return dodatniInfoUser;
    }

    public Ucesnici dodatniInfoUser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniInfoUser = dodatniInfoUser;
        return this;
    }

    public void setDodatniInfoUser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniInfoUser = dodatniInfoUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ucesnici)) {
            return false;
        }
        return id != null && id.equals(((Ucesnici) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ucesnici{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}

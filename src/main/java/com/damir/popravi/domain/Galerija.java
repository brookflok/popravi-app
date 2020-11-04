package com.damir.popravi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

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

    @Column(name = "title")
    private String title;

    @Column(name = "created")
    private Instant created;

    @OneToOne
    @JoinColumn(unique = true)
    private Artikl artikl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Galerija title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreated() {
        return created;
    }

    public Galerija created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
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
            ", title='" + getTitle() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}

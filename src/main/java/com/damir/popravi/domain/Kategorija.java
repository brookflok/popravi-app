package com.damir.popravi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Kategorija.
 */
@Entity
@Table(name = "kategorija")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "kategorija")
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "kategorija")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Artikl> artikls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Kategorija name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Artikl> getArtikls() {
        return artikls;
    }

    public Kategorija artikls(Set<Artikl> artikls) {
        this.artikls = artikls;
        return this;
    }

    public Kategorija addArtikl(Artikl artikl) {
        this.artikls.add(artikl);
        artikl.setKategorija(this);
        return this;
    }

    public Kategorija removeArtikl(Artikl artikl) {
        this.artikls.remove(artikl);
        artikl.setKategorija(null);
        return this;
    }

    public void setArtikls(Set<Artikl> artikls) {
        this.artikls = artikls;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kategorija)) {
            return false;
        }
        return id != null && id.equals(((Kategorija) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kategorija{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

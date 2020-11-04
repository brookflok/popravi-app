package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ProfilnaSlika.
 */
@Entity
@Table(name = "profilna_slika")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "profilnaslika")
public class ProfilnaSlika implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "created")
    private Instant created;

    @OneToOne(mappedBy = "profilnaSlika")
    @JsonIgnore
    private DodatniInfoUser dodatniInfoUser;

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

    public ProfilnaSlika title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreated() {
        return created;
    }

    public ProfilnaSlika created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public DodatniInfoUser getDodatniInfoUser() {
        return dodatniInfoUser;
    }

    public ProfilnaSlika dodatniInfoUser(DodatniInfoUser dodatniInfoUser) {
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
        if (!(o instanceof ProfilnaSlika)) {
            return false;
        }
        return id != null && id.equals(((ProfilnaSlika) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfilnaSlika{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}

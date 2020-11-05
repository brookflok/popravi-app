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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "ucesnici_dodatni_info_user",
               joinColumns = @JoinColumn(name = "ucesnici_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "dodatni_info_user_id", referencedColumnName = "id"))
    private Set<DodatniInfoUser> dodatniInfoUsers = new HashSet<>();

    @OneToOne(mappedBy = "ucesnici")
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

    public Ucesnici datum(Instant datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public Set<DodatniInfoUser> getDodatniInfoUsers() {
        return dodatniInfoUsers;
    }

    public Ucesnici dodatniInfoUsers(Set<DodatniInfoUser> dodatniInfoUsers) {
        this.dodatniInfoUsers = dodatniInfoUsers;
        return this;
    }

    public Ucesnici addDodatniInfoUser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniInfoUsers.add(dodatniInfoUser);
        dodatniInfoUser.getUcesnicis().add(this);
        return this;
    }

    public Ucesnici removeDodatniInfoUser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniInfoUsers.remove(dodatniInfoUser);
        dodatniInfoUser.getUcesnicis().remove(this);
        return this;
    }

    public void setDodatniInfoUsers(Set<DodatniInfoUser> dodatniInfoUsers) {
        this.dodatniInfoUsers = dodatniInfoUsers;
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

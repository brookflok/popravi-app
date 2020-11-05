package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DodatniInfoUser.
 */
@Entity
@Table(name = "dodatni_info_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dodatniinfouser")
public class DodatniInfoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "korisnickoime")
    private String korisnickoime;

    @Column(name = "broj_telefona")
    private String brojTelefona;

    @Column(name = "majstor")
    private Boolean majstor;

    @Column(name = "postoji")
    private Boolean postoji;

    @Column(name = "detaljni_opis")
    private String detaljniOpis;

    @OneToOne
    @JoinColumn(unique = true)
    private Lokacija lokacija;

    @OneToOne
    @JoinColumn(unique = true)
    private ProfilnaSlika profilnaSlika;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "dodatniinfouser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Artikl> artikls = new HashSet<>();

    @ManyToMany(mappedBy = "dodatniInfoUsers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Ucesnici> ucesnicis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnickoime() {
        return korisnickoime;
    }

    public DodatniInfoUser korisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
        return this;
    }

    public void setKorisnickoime(String korisnickoime) {
        this.korisnickoime = korisnickoime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public DodatniInfoUser brojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
        return this;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public Boolean isMajstor() {
        return majstor;
    }

    public DodatniInfoUser majstor(Boolean majstor) {
        this.majstor = majstor;
        return this;
    }

    public void setMajstor(Boolean majstor) {
        this.majstor = majstor;
    }

    public Boolean isPostoji() {
        return postoji;
    }

    public DodatniInfoUser postoji(Boolean postoji) {
        this.postoji = postoji;
        return this;
    }

    public void setPostoji(Boolean postoji) {
        this.postoji = postoji;
    }

    public String getDetaljniOpis() {
        return detaljniOpis;
    }

    public DodatniInfoUser detaljniOpis(String detaljniOpis) {
        this.detaljniOpis = detaljniOpis;
        return this;
    }

    public void setDetaljniOpis(String detaljniOpis) {
        this.detaljniOpis = detaljniOpis;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public DodatniInfoUser lokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
        return this;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public ProfilnaSlika getProfilnaSlika() {
        return profilnaSlika;
    }

    public DodatniInfoUser profilnaSlika(ProfilnaSlika profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
        return this;
    }

    public void setProfilnaSlika(ProfilnaSlika profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public User getUser() {
        return user;
    }

    public DodatniInfoUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Artikl> getArtikls() {
        return artikls;
    }

    public DodatniInfoUser artikls(Set<Artikl> artikls) {
        this.artikls = artikls;
        return this;
    }

    public DodatniInfoUser addArtikl(Artikl artikl) {
        this.artikls.add(artikl);
        artikl.setDodatniinfouser(this);
        return this;
    }

    public DodatniInfoUser removeArtikl(Artikl artikl) {
        this.artikls.remove(artikl);
        artikl.setDodatniinfouser(null);
        return this;
    }

    public void setArtikls(Set<Artikl> artikls) {
        this.artikls = artikls;
    }

    public Set<Ucesnici> getUcesnicis() {
        return ucesnicis;
    }

    public DodatniInfoUser ucesnicis(Set<Ucesnici> ucesnicis) {
        this.ucesnicis = ucesnicis;
        return this;
    }

    public DodatniInfoUser addUcesnici(Ucesnici ucesnici) {
        this.ucesnicis.add(ucesnici);
        ucesnici.getDodatniInfoUsers().add(this);
        return this;
    }

    public DodatniInfoUser removeUcesnici(Ucesnici ucesnici) {
        this.ucesnicis.remove(ucesnici);
        ucesnici.getDodatniInfoUsers().remove(this);
        return this;
    }

    public void setUcesnicis(Set<Ucesnici> ucesnicis) {
        this.ucesnicis = ucesnicis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DodatniInfoUser)) {
            return false;
        }
        return id != null && id.equals(((DodatniInfoUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DodatniInfoUser{" +
            "id=" + getId() +
            ", korisnickoime='" + getKorisnickoime() + "'" +
            ", brojTelefona='" + getBrojTelefona() + "'" +
            ", majstor='" + isMajstor() + "'" +
            ", postoji='" + isPostoji() + "'" +
            ", detaljniOpis='" + getDetaljniOpis() + "'" +
            "}";
    }
}

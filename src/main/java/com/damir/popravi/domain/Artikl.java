package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Artikl.
 */
@Entity
@Table(name = "artikl")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "artikl")
public class Artikl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "kratki_opis")
    private String kratkiOpis;

    @Column(name = "detaljni_opis")
    private String detaljniOpis;

    @Column(name = "majstor")
    private Boolean majstor;

    @Column(name = "postoji")
    private Boolean postoji;

    @OneToOne
    @JoinColumn(unique = true)
    private Lokacija lokacija;

    @OneToOne
    @JoinColumn(unique = true)
    private Potreba potreba;

    @OneToOne
    @JoinColumn(unique = true)
    private Usluga usluga;

    @OneToOne
    @JoinColumn(unique = true)
    private Galerija galerija;

    @OneToOne
    @JoinColumn(unique = true)
    private MainSlika mainSlika;

    @OneToOne
    @JoinColumn(unique = true)
    private Informacije informacije;

    @OneToOne
    @JoinColumn(unique = true)
    private GrupacijaPitanja grupacijaPitanja;

    @ManyToOne
    @JsonIgnoreProperties(value = "artikls", allowSetters = true)
    private DodatniInfoUser dodatniinfouser;

    @ManyToOne
    @JsonIgnoreProperties(value = "artikls", allowSetters = true)
    private Kategorija kategorija;

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

    public Artikl ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getKratkiOpis() {
        return kratkiOpis;
    }

    public Artikl kratkiOpis(String kratkiOpis) {
        this.kratkiOpis = kratkiOpis;
        return this;
    }

    public void setKratkiOpis(String kratkiOpis) {
        this.kratkiOpis = kratkiOpis;
    }

    public String getDetaljniOpis() {
        return detaljniOpis;
    }

    public Artikl detaljniOpis(String detaljniOpis) {
        this.detaljniOpis = detaljniOpis;
        return this;
    }

    public void setDetaljniOpis(String detaljniOpis) {
        this.detaljniOpis = detaljniOpis;
    }

    public Boolean isMajstor() {
        return majstor;
    }

    public Artikl majstor(Boolean majstor) {
        this.majstor = majstor;
        return this;
    }

    public void setMajstor(Boolean majstor) {
        this.majstor = majstor;
    }

    public Boolean isPostoji() {
        return postoji;
    }

    public Artikl postoji(Boolean postoji) {
        this.postoji = postoji;
        return this;
    }

    public void setPostoji(Boolean postoji) {
        this.postoji = postoji;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public Artikl lokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
        return this;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public Potreba getPotreba() {
        return potreba;
    }

    public Artikl potreba(Potreba potreba) {
        this.potreba = potreba;
        return this;
    }

    public void setPotreba(Potreba potreba) {
        this.potreba = potreba;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public Artikl usluga(Usluga usluga) {
        this.usluga = usluga;
        return this;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

    public Galerija getGalerija() {
        return galerija;
    }

    public Artikl galerija(Galerija galerija) {
        this.galerija = galerija;
        return this;
    }

    public void setGalerija(Galerija galerija) {
        this.galerija = galerija;
    }

    public MainSlika getMainSlika() {
        return mainSlika;
    }

    public Artikl mainSlika(MainSlika mainSlika) {
        this.mainSlika = mainSlika;
        return this;
    }

    public void setMainSlika(MainSlika mainSlika) {
        this.mainSlika = mainSlika;
    }

    public Informacije getInformacije() {
        return informacije;
    }

    public Artikl informacije(Informacije informacije) {
        this.informacije = informacije;
        return this;
    }

    public void setInformacije(Informacije informacije) {
        this.informacije = informacije;
    }

    public GrupacijaPitanja getGrupacijaPitanja() {
        return grupacijaPitanja;
    }

    public Artikl grupacijaPitanja(GrupacijaPitanja grupacijaPitanja) {
        this.grupacijaPitanja = grupacijaPitanja;
        return this;
    }

    public void setGrupacijaPitanja(GrupacijaPitanja grupacijaPitanja) {
        this.grupacijaPitanja = grupacijaPitanja;
    }

    public DodatniInfoUser getDodatniinfouser() {
        return dodatniinfouser;
    }

    public Artikl dodatniinfouser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniinfouser = dodatniInfoUser;
        return this;
    }

    public void setDodatniinfouser(DodatniInfoUser dodatniInfoUser) {
        this.dodatniinfouser = dodatniInfoUser;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public Artikl kategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
        return this;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artikl)) {
            return false;
        }
        return id != null && id.equals(((Artikl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artikl{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", kratkiOpis='" + getKratkiOpis() + "'" +
            ", detaljniOpis='" + getDetaljniOpis() + "'" +
            ", majstor='" + isMajstor() + "'" +
            ", postoji='" + isPostoji() + "'" +
            "}";
    }
}

package com.damir.popravi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Slika.
 */
@Entity
@Table(name = "slika")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "slika")
public class Slika implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "uploaded")
    private Instant uploaded;

    @OneToOne
    @JoinColumn(unique = true)
    private MainSlika mainslika;

    @OneToOne
    @JoinColumn(unique = true)
    private ProfilnaSlika mainslika;

    @ManyToOne
    @JsonIgnoreProperties(value = "slikas", allowSetters = true)
    private Galerija galerija;

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

    public Slika ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public byte[] getImage() {
        return image;
    }

    public Slika image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Slika imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Instant getUploaded() {
        return uploaded;
    }

    public Slika uploaded(Instant uploaded) {
        this.uploaded = uploaded;
        return this;
    }

    public void setUploaded(Instant uploaded) {
        this.uploaded = uploaded;
    }

    public MainSlika getMainslika() {
        return mainslika;
    }

    public Slika mainslika(MainSlika mainSlika) {
        this.mainslika = mainSlika;
        return this;
    }

    public void setMainslika(MainSlika mainSlika) {
        this.mainslika = mainSlika;
    }

    public ProfilnaSlika getMainslika() {
        return mainslika;
    }

    public Slika mainslika(ProfilnaSlika profilnaSlika) {
        this.mainslika = profilnaSlika;
        return this;
    }

    public void setMainslika(ProfilnaSlika profilnaSlika) {
        this.mainslika = profilnaSlika;
    }

    public Galerija getGalerija() {
        return galerija;
    }

    public Slika galerija(Galerija galerija) {
        this.galerija = galerija;
        return this;
    }

    public void setGalerija(Galerija galerija) {
        this.galerija = galerija;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Slika)) {
            return false;
        }
        return id != null && id.equals(((Slika) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Slika{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", uploaded='" + getUploaded() + "'" +
            "}";
    }
}

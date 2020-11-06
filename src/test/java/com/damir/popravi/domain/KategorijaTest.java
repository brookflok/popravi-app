package com.damir.popravi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.damir.popravi.web.rest.TestUtil;

public class KategorijaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kategorija.class);
        Kategorija kategorija1 = new Kategorija();
        kategorija1.setId(1L);
        Kategorija kategorija2 = new Kategorija();
        kategorija2.setId(kategorija1.getId());
        assertThat(kategorija1).isEqualTo(kategorija2);
        kategorija2.setId(2L);
        assertThat(kategorija1).isNotEqualTo(kategorija2);
        kategorija1.setId(null);
        assertThat(kategorija1).isNotEqualTo(kategorija2);
    }
}

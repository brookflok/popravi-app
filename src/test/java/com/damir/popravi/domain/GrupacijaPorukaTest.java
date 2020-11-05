package com.damir.popravi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.damir.popravi.web.rest.TestUtil;

public class GrupacijaPorukaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupacijaPoruka.class);
        GrupacijaPoruka grupacijaPoruka1 = new GrupacijaPoruka();
        grupacijaPoruka1.setId(1L);
        GrupacijaPoruka grupacijaPoruka2 = new GrupacijaPoruka();
        grupacijaPoruka2.setId(grupacijaPoruka1.getId());
        assertThat(grupacijaPoruka1).isEqualTo(grupacijaPoruka2);
        grupacijaPoruka2.setId(2L);
        assertThat(grupacijaPoruka1).isNotEqualTo(grupacijaPoruka2);
        grupacijaPoruka1.setId(null);
        assertThat(grupacijaPoruka1).isNotEqualTo(grupacijaPoruka2);
    }
}

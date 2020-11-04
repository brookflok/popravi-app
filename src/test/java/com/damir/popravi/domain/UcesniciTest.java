package com.damir.popravi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.damir.popravi.web.rest.TestUtil;

public class UcesniciTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ucesnici.class);
        Ucesnici ucesnici1 = new Ucesnici();
        ucesnici1.setId(1L);
        Ucesnici ucesnici2 = new Ucesnici();
        ucesnici2.setId(ucesnici1.getId());
        assertThat(ucesnici1).isEqualTo(ucesnici2);
        ucesnici2.setId(2L);
        assertThat(ucesnici1).isNotEqualTo(ucesnici2);
        ucesnici1.setId(null);
        assertThat(ucesnici1).isNotEqualTo(ucesnici2);
    }
}

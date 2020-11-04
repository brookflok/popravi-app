package com.damir.popravi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.damir.popravi.web.rest.TestUtil;

public class GrupacijaPitanjaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupacijaPitanja.class);
        GrupacijaPitanja grupacijaPitanja1 = new GrupacijaPitanja();
        grupacijaPitanja1.setId(1L);
        GrupacijaPitanja grupacijaPitanja2 = new GrupacijaPitanja();
        grupacijaPitanja2.setId(grupacijaPitanja1.getId());
        assertThat(grupacijaPitanja1).isEqualTo(grupacijaPitanja2);
        grupacijaPitanja2.setId(2L);
        assertThat(grupacijaPitanja1).isNotEqualTo(grupacijaPitanja2);
        grupacijaPitanja1.setId(null);
        assertThat(grupacijaPitanja1).isNotEqualTo(grupacijaPitanja2);
    }
}

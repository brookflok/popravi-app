package com.damir.popravi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.damir.popravi.web.rest.TestUtil;

public class PotrebaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Potreba.class);
        Potreba potreba1 = new Potreba();
        potreba1.setId(1L);
        Potreba potreba2 = new Potreba();
        potreba2.setId(potreba1.getId());
        assertThat(potreba1).isEqualTo(potreba2);
        potreba2.setId(2L);
        assertThat(potreba1).isNotEqualTo(potreba2);
        potreba1.setId(null);
        assertThat(potreba1).isNotEqualTo(potreba2);
    }
}

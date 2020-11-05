package com.damir.popravi.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.damir.popravi.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.damir.popravi.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.damir.popravi.domain.User.class.getName());
            createCache(cm, com.damir.popravi.domain.Authority.class.getName());
            createCache(cm, com.damir.popravi.domain.User.class.getName() + ".authorities");
            createCache(cm, com.damir.popravi.domain.DodatniInfoUser.class.getName());
            createCache(cm, com.damir.popravi.domain.DodatniInfoUser.class.getName() + ".artikls");
            createCache(cm, com.damir.popravi.domain.Artikl.class.getName());
            createCache(cm, com.damir.popravi.domain.Usluga.class.getName());
            createCache(cm, com.damir.popravi.domain.Potreba.class.getName());
            createCache(cm, com.damir.popravi.domain.Galerija.class.getName());
            createCache(cm, com.damir.popravi.domain.MainSlika.class.getName());
            createCache(cm, com.damir.popravi.domain.ProfilnaSlika.class.getName());
            createCache(cm, com.damir.popravi.domain.Slika.class.getName());
            createCache(cm, com.damir.popravi.domain.Informacije.class.getName());
            createCache(cm, com.damir.popravi.domain.JavnoPitanje.class.getName());
            createCache(cm, com.damir.popravi.domain.OdgovorNaJavnoPitanje.class.getName());
            createCache(cm, com.damir.popravi.domain.Entiteti.class.getName());
            createCache(cm, com.damir.popravi.domain.Kanton.class.getName());
            createCache(cm, com.damir.popravi.domain.Lokacija.class.getName());
            createCache(cm, com.damir.popravi.domain.Ucesnici.class.getName());
            createCache(cm, com.damir.popravi.domain.Ucesnici.class.getName() + ".dodatniInfoUsers");
            createCache(cm, com.damir.popravi.domain.Poruka.class.getName());
            createCache(cm, com.damir.popravi.domain.Chat.class.getName());
            createCache(cm, com.damir.popravi.domain.DodatniInfoUser.class.getName() + ".ucesnicis");
            createCache(cm, com.damir.popravi.domain.GrupacijaPitanja.class.getName());
            createCache(cm, com.damir.popravi.domain.Galerija.class.getName() + ".slikas");
            createCache(cm, com.damir.popravi.domain.GrupacijaPoruka.class.getName());
            createCache(cm, com.damir.popravi.domain.GrupacijaPoruka.class.getName() + ".porukas");
            createCache(cm, com.damir.popravi.domain.GrupacijaPitanja.class.getName() + ".javnoPitanjes");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}

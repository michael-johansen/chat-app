package no.ciber.chat.config;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import static org.hibernate.cfg.AvailableSettings.*;

/**
 * User: Michael Johansen
 * Date: 11.10.13
 * Time: 23:31
 */
@Configuration
@EnableJpaRepositories(basePackages = {"no.ciber.chat.repositories"})
@EnableCaching
public class PersistenceConfig {

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        emfBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfBean.setPackagesToScan("no.ciber.chat.model");
        emfBean.getJpaPropertyMap().put(HBM2DDL_AUTO, "create");
        emfBean.getJpaPropertyMap().put(FORMAT_SQL, true);
        emfBean.getJpaPropertyMap().put(GENERATE_STATISTICS, true);
        emfBean.getJpaPropertyMap().put(USE_SECOND_LEVEL_CACHE, true);
        emfBean.getJpaPropertyMap().put(USE_QUERY_CACHE, true);
        emfBean.getJpaPropertyMap().put(CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");

        emfBean.setDataSource(dataSource);
        emfBean.afterPropertiesSet();

        return emfBean.getObject();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:h2:~/test;AUTO_SERVER=TRUE");
        driverManagerDataSource.setUsername("sa");
        driverManagerDataSource.setPassword("");
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        return new LazyConnectionDataSourceProxy(driverManagerDataSource);
    }

    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslator persistenceExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {
        return new EhCacheCacheManager(CacheManager.create()) {
            @Override
            public Cache getCache(String name) {
                Cache cache = super.getCache(name);
                if (cache == null) {
                    getCacheManager().addCache(name);
                    return super.getCache(name);
                }
                return cache;
            }
        };
    }

    @Autowired
    public void initData() throws IOException {
        ;
    }
}

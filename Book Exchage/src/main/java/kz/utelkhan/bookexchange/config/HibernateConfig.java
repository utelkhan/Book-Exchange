package kz.utelkhan.bookexchange.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
@Import(MainConfig.class)
public class HibernateConfig {
    MainConfig mainConfig;

    @Autowired
    public HibernateConfig(MainConfig mainConfig) {
        this.mainConfig = mainConfig;
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(mainConfig.dataSource());
        sessionFactoryBean.setPackagesToScan("kz.utelkhan.bookexchange.model");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    protected Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.hbm2ddl.auto", "create");
        return hibernateProp;
    }
}
package ru.cadmy.blog.configuration;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceConfiguration {
    DataSource dataSource();

    Properties additionalProperties();
}

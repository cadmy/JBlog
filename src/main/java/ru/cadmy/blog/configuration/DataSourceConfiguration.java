package ru.cadmy.blog.configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Cadmy on 27.04.2016.
 */
public interface DataSourceConfiguration {
    DataSource dataSource();

    Properties additionalProperties();
}

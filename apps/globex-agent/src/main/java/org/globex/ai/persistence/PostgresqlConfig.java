package org.globex.ai.persistence;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "postgresql")
public interface PostgresqlConfig {

    String username();

    String password();

    String host();

    int port();

    String database();

}

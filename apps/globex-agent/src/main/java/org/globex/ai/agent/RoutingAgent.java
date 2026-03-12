package org.globex.ai.agent;

import io.quarkus.arc.Unremovable;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Identifier("routing-agent")
@Unremovable
public class RoutingAgent extends BaseAgent implements Agent {

    @Override
    public String sendRequestToAgent() {
        return null;
    }

}

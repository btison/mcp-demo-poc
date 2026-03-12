package org.globex.ai.graph;

import java.util.Map;

public interface StateAction {

    Map<String, Object> apply(String value, Map<String, Object> state);

}

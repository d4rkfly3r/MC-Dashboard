package com.github.d4rkfly3r.mcdash.src.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Route {
    private final String key;
    private final Consumer<HTTPHeaderParser> consumer;
    private final List<Route> children;

    public Route(String key, Consumer<HTTPHeaderParser> consumer) {
        this.key = key;
        this.consumer = consumer;

        this.children = new ArrayList<>();
    }

    public void call(HTTPHeaderParser httpHeaderParser) {
        this.consumer.accept(httpHeaderParser);
    }

    public Route addChild(Route child) {
        if (!children.contains(child)) {
            children.add(child);
            children.sort((o1, o2) -> o1.key.compareTo(o2.key));
        }
        return this;
    }

    public Consumer<HTTPHeaderParser> getConsumer() {
        return consumer;
    }

    public List<Route> getChildren() {
        return children;
    }

    public String getKey() {
        return key;
    }
}

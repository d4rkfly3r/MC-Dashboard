package com.github.d4rkfly3r.mcdash.src.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Routes {

    private static Route rootRoute = new Route("", System.err::println).addChild(new Route("index", System.out::println)).addChild(
            new Route("test", null).addChild(new Route("index", System.out::println))
    );

    public static boolean routeAvailable(HTTPHeaderParser headers) {
        return routeAvailable(headers.getRequestURL());
    }

    public static boolean routeAvailable(String requestURL) {
        List<String> segments = new ArrayList<>(Arrays.asList(requestURL.split("/")));
        if (segments.size() > 0) segments.remove(0);

        return segments.size() == 0 ? rootRoute.getConsumer() != null : iterateThroughRoutes(rootRoute, segments);
    }

    private static boolean iterateThroughRoutes(Route rootRoute, List<String> segments) {
        System.out.println(rootRoute.getChildren().size() + " | " + segments);
        if (rootRoute.getChildren().size() == 0 && segments.size() == 0) {
            return true;
        }
        if ((rootRoute.getChildren().size() == 0 && segments.size() != 0) || segments.size() == 0) {
            return false;
        }
        System.out.println("M");
        Optional<Route> tr = rootRoute.getChildren().parallelStream().filter(route -> route.getKey().equalsIgnoreCase(segments.get(0))).findFirst();
        if (tr.isPresent()) {
            segments.remove(0);
            return iterateThroughRoutes(tr.get(), segments);
        } else {
            return false;
        }
    }

    public static boolean isRouteSpecial(HTTPHeaderParser headers) {
        return false;
    }
}

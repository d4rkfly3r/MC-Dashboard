package com.github.d4rkfly3r.mcdash.src.util;

import com.github.d4rkfly3r.mcdash.src.MainClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Routes {

    private static Route rootRoute = new Route("", MainClass.CONSTANTS.indexPage).addChild(new Route("index", null)).addChild(
            new Route("test", null).addChild(new Route("index", null))
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
        if (rootRoute.getChildren().size() == 0 && segments.size() == 0) {
            return rootRoute.getConsumer() != null;
        }
        if ((rootRoute.getChildren().size() == 0 && segments.size() != 0) || segments.size() == 0) {
            return false;
        }
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

    public static Route getRoute(HTTPHeaderParser headers) {
        List<String> segments = new ArrayList<>(Arrays.asList(headers.getRequestURL().split("/")));
        return segments.size() == 0 ? rootRoute : getRoute(rootRoute, segments);
    }

    private static Route getRoute(Route rootRoute, List<String> segments) {
        if (rootRoute.getChildren().size() == 0 && segments.size() == 0) {
            return rootRoute;
        }
        if ((rootRoute.getChildren().size() == 0 && segments.size() != 0) || segments.size() == 0) {
            return null;
        }
        Optional<Route> tr = rootRoute.getChildren().parallelStream().filter(route -> route.getKey().equalsIgnoreCase(segments.get(0))).findFirst();
        if (tr.isPresent()) {
            segments.remove(0);
            return getRoute(tr.get(), segments);
        } else {
            return null;
        }
    }
}

package de.dm.playground.springgateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

@Configuration
public class RouteConfigurer {

  @Bean
  public RouteLocator configureCustomRouteLocator(RouteLocatorBuilder builder) {

    return builder.routes().route("helloRoute",
        route -> route.path("/service/hello").filters(filter -> filter.filter((exchange, chain) -> {

          ServerHttpRequest request = exchange.getRequest().mutate()
              .path("/helloworld").build();

          String nameParam = request.getQueryParams().getFirst("name");

          ServerHttpResponse response = exchange.getResponse();
          response.getHeaders().add("X-Hello", nameParam);

          return chain.filter(exchange.mutate().request(request).build());
        })).uri("lb://proto-business-helloworld"))
        .route("gwInfoRoute"
            + "", route -> route.path("/service/gwinfo")
            .filters(filter -> filter.filter((exchange, chain) -> {
              ServerHttpRequest request = exchange.getRequest().mutate().path("/controllers/gwinfo")
                  .build();

              return chain.filter(exchange.mutate().request(request).build());
            })).uri("lb://proto-infrastructure-gateway"))
        .build();
  }
}

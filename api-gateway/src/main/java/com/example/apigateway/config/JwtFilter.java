package com.example.apigateway.config;

import com.example.apigateway.service.JwtService;
import com.example.apigateway.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // "/auth" ve "/user/register" yoluna gelen isteklerde JWT doğrulaması yapma
        if (exchange.getRequest().getPath().contextPath().equals("/auth") ||
                exchange.getRequest().getPath().contextPath().equals("/user/register")) {
            return chain.filter(exchange);
        }

        // Authorization başlığını al
        final String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        final String jwtToken;
        final String userName;

        // Authorization başlığı varsa ve Bearer ile başlıyorsa token'ı al
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        jwtToken = authorizationHeader.substring(7); // "Bearer " kısmını kaldır
        userName = jwtService.extractUserName(jwtToken); // Token'dan kullanıcı adını çıkar

        // Kullanıcı adı varsa ve SecurityContext'te oturum açılmamışsa
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // UserClient üzerinden kullanıcı bilgilerini al
            return userDetailService.findByUsername(userName)
                    .flatMap(userDetails -> {
                        // Token geçerliyse kullanıcıyı authenticate et
                        if (jwtService.validateToken(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                            // Reactive SecurityContext'i ayarla
                            SecurityContext securityContext = new SecurityContextImpl(authenticationToken);

                            // Güvenlik bağlamını ayarla ve filtreyi devam ettir
                            return chain.filter(exchange)
                                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                        }
                        return chain.filter(exchange); // Token geçersizse isteği devam ettir
                    });
        }

        // JWT doğrulaması yapılmazsa istek olduğu gibi devam etsin
        return chain.filter(exchange);
    }
}

package actiOn.config;

import actiOn.auth.filter.JwtAuthenticationFilter;
import actiOn.auth.filter.JwtVerificationFilter;
import actiOn.auth.handler.MemberAccessDeniedHandler;
import actiOn.auth.handler.MemberAuthenticationEntryPoint;
import actiOn.auth.handler.MemberAuthenticationFailureHandler;
import actiOn.auth.handler.MemberAuthenticationSuccessHandler;
import actiOn.auth.oauth2.OAuth2MemberSuccessHandler;
import actiOn.auth.provider.TokenProvider;
import actiOn.auth.role.RoleService;
import actiOn.auth.utils.MemberAuthorityUtil;
import actiOn.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static actiOn.auth.utils.TokenPrefix.REFRESH;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final MemberAuthorityUtil authorityUtil;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ApplicationContext context = getApplicationContext();

        httpSecurity
                .headers().frameOptions().sameOrigin()

                .and()
                .csrf().disable()
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())

                .and()
                .apply(new CustomFilterConfigurer(context.getBean(MemberService.class)))

                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(((request, response, authentication) -> {
                    response.setHeader("Set-Cookie", REFRESH.getType() +
                            "=; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=0;");
                    response.setHeader("Access-Control-Allow-Origin", "http://ac-ti-on.s3-website.ap-northeast-2.amazonaws.com");
                }))
                .logoutSuccessUrl("http://ac-ti-on.s3-website.ap-northeast-2.amazonaws.com/home")

                .and()
                .authorizeRequests(this::configureAuthorization)
                .oauth2Login(oAuth2 -> oAuth2
                        .successHandler(new OAuth2MemberSuccessHandler(
                                context.getBean(MemberService.class), context.getBean(RoleService.class), tokenProvider)
                        )
                );
    }

    private void configureAuthorization
            (ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorize) {
        String USER = authorityUtil.getUSER();
        String PARTNER = authorityUtil.getPARTNER();

        authorize
                // PARTNER 권한
                .mvcMatchers(GET, "/mystores").hasRole(PARTNER)
                .mvcMatchers(GET, "/mypage/partner").hasRole(PARTNER)
                .mvcMatchers(POST, "/stores").hasRole(PARTNER)
                .mvcMatchers(PATCH, "/stores/{store-id}").hasRole(PARTNER)
                .mvcMatchers(DELETE, "/stores").hasRole(PARTNER)
                .mvcMatchers(POST, "/storeImages/**").hasRole(PARTNER)

                // USER 권한
                .mvcMatchers(POST, "/stores/favorites/{store-id}").hasRole(USER)
                .mvcMatchers(DELETE, "/stores/favorites/{store-id}").hasRole(USER)
                .mvcMatchers("/payments/**").hasRole(USER)
                .mvcMatchers("/partners/**").hasRole(USER)
                .mvcMatchers("/reservations/**").hasRole(USER)
                .mvcMatchers(POST, "/reviews").hasRole(USER)
                .mvcMatchers(GET, "/mypage/**").hasRole(USER)

                .mvcMatchers("/").permitAll();
    }

    // JwtAuthenticationFilter 구성하는 클래스
    @AllArgsConstructor
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        private final MemberService memberService;

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(authenticationManager);
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(tokenProvider));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(tokenProvider, authorityUtil, memberService);

            // Spring Security Filter Chain에 추가
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }

    // 구체적인 CORS 정책 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                Arrays.asList(
                        "http://localhost:3000",
                        "https://acti-on.netlify.app",
                        "http://localhost:5173",
                        "http://ec2-52-78-205-102.ap-northeast-2.compute.amazonaws.com:8080",
                        "http://ac-ti-on.s3-website.ap-northeast-2.amazonaws.com"
                )
        );
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(2000L);
//        configuration.setAllowedHeaders(Arrays.asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "Refresh", "Set-Cookie"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

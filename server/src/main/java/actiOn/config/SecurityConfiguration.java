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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final TokenProvider tokenProvider;
    private final MemberAuthorityUtil authorityUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MemberService memberService, RoleService roleService) throws Exception {
        httpSecurity
                .headers().frameOptions().sameOrigin()

                .and()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())

                .and()
                .apply(new CustomFilterConfigurer(memberService))

                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() /// Todo URI 권한 레벨 설정
                )
                .oauth2Login(oAuth2 -> oAuth2
                        .successHandler(new OAuth2MemberSuccessHandler(memberService, roleService, tokenProvider))
                );

        return httpSecurity.build();
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
                        "http://ec2-52-78-205-102.ap-northeast-2.compute.amazonaws.com"
                        // TODO S3 엔드포인트 추가 ""
//                        "https://5c36-121-176-132-24.ngrok-free.app" //여기 임시 url
                )
        );
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(200L);
        configuration.setAllowedHeaders(Arrays.asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "Refresh", "Set-Cookie"));
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

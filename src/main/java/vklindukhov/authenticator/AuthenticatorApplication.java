package vklindukhov.authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(2147483640) // SecurityProperties.ACCESS_OVERRIDE_ORDER = 2147483640
@SuppressWarnings("squid:CommentedOutCodeLine")
public class AuthenticatorApplication extends WebSecurityConfigurerAdapter {
    private final OAuth2ClientContext oauth2ClientContext;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticatorApplication.class, args);
    }

    @Autowired
    @SuppressWarnings("all")
    public AuthenticatorApplication(
            final OAuth2ClientContext oauth2ClientContext
    ) {
        this.oauth2ClientContext = oauth2ClientContext;
    }

    @SuppressWarnings("unused")
    @RequestMapping({"/user", "/me"})
    public Map<String, String> user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
//                .and().logout().logoutSuccessUrl("/").permitAll()
//                .and().csrf().csrfTokenRepository(withHttpOnlyFalse())
//                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
        ;
    }

//    @Bean
//    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//
//        return registration;
//    }
//
//    @Bean
//    @ConfigurationProperties("security.facebook")
//    public ClientResources facebook() {
//        return new ClientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("security.facebook.resource")
//    public ResourceServerProperties facebookResource() {
//        return new ResourceServerProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("security.github")
//    public ClientResources github() {
//        return new ClientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("security.github.resource")
//    public ResourceServerProperties githubResource() {
//        return new ResourceServerProperties();
//
//    }
//
//    @SuppressWarnings("unused")
//    private Filter ssoFilter() {
//        CompositeFilter filter = new CompositeFilter();
//        List<Filter> filters = new ArrayList<>();
//        filters.add(ssoFilter(facebook(), "/login/facebook"));
//        filters.add(ssoFilter(github(), "/login/github"));
//        filter.setFilters(filters);
//
//        return filter;
//    }
//
//    private Filter ssoFilter(ClientResources client, String path) {
//        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
//        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
//        filter.setRestTemplate(template);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
//        tokenServices.setRestTemplate(template);
//        filter.setTokenServices(tokenServices);
//
//        return filter;
//    }
//
//    class ClientResources {
//
//        @NestedConfigurationProperty
//        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
//
//        @NestedConfigurationProperty
//        private ResourceServerProperties resource = new ResourceServerProperties();
//
//        AuthorizationCodeResourceDetails getClient() {
//            return client;
//        }
//
//        ResourceServerProperties getResource() {
//            return resource;
//        }
//    }
//
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/me")
                    .authorizeRequests()
                    .anyRequest().authenticated();
        }
    }
}

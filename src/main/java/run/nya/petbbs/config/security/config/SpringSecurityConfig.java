package run.nya.petbbs.config.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Spring Security
 *
 * 2021/02/18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // White List
        List<String> urls = ignoreUrlsConfig.getUrls();
        for (String url : urls) {
            httpSecurity.authorizeRequests().antMatchers(url).permitAll();
        }

        httpSecurity.csrf().disable();
    }
}

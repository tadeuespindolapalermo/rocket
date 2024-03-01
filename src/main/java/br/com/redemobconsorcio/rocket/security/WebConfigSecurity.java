package br.com.redemobconsorcio.rocket.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	private final ImplementacaoUserDetailsService implementacaoUserDetailsService;

    public WebConfigSecurity(ImplementacaoUserDetailsService implementacaoUserDetailsService) {
        this.implementacaoUserDetailsService = implementacaoUserDetailsService;
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/captacaoCandidato/**", "/login", "/index", "/").permitAll()
			.antMatchers(HttpMethod.POST, "/salvaPessoa/**").permitAll()
			.antMatchers(HttpMethod.GET, "/aprovacaoCandidaturas", "/baixarFoto/**", "/aprovacaoCandidatura/**").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/consultaCandidato").hasAnyRole("CANDIDATO")
			.antMatchers("/materialize/**").permitAll()
			.anyRequest().authenticated().and().formLogin().permitAll()
			.loginPage("/login")
			.defaultSuccessUrl("/aprovacaoCandidaturas")
			.failureUrl("/login?error=true")
			.and().logout()
			.logoutSuccessUrl("/index")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/materialize/**");
	}

}

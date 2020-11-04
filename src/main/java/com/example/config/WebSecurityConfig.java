package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	// フォームの値と比較するDBから取得したパスワードは暗号化されているからフォームの値も暗号化するために利用
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	@Override
	public void configure(WebSecurity web)throws Exception{
		web.ignoring().antMatchers("/images/**","/css/**","/javascript/**");
	}
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
	.formLogin()
		.loginPage("/login") // ログインページはコントローラを経由しないのでViewNameとの紐づけが必要
		.loginProcessingUrl("/sign_in")// フォームのSubmitURL。。このURLｈリクエストが送られると認証処理が実行される
		.usernameParameter("username")//リクエストパラメータのname属性を明示
		.passwordParameter("password")
		.successForwardUrl("/hello")// ログイン成功したらhello.htmlへ遷移
		.failureUrl("/login?error")// ログイン失敗時はエラーでlogin.htmlへ遷移
		.permitAll()
		.and()
	.logout()
	    .logoutUrl("/logout")
	    .logoutSuccessUrl("/login?logout")
	    .permitAll();
	    
	}
	@Autowired
	public void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.inMemoryAuthentication()
		        .withUser("user").password("{noop}password").roles("USER");
	}

}

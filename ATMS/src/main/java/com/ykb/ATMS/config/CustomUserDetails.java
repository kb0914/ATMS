package com.ykb.ATMS.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

	private Collection<? extends GrantedAuthority> authorities;

	private long id;

	private String password;

	private String username;

	private Boolean enabled;

	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private boolean credentialsNonExpired;
	
	public CustomUserDetails() {}

	public CustomUserDetails(long id, String username, String password,Collection<? extends GrantedAuthority> authorities) {
	    this.id = id;
	    this.enabled=true;
	    this.username=username;
	    this.password=password;
	    this.accountNonExpired=true;
	    this.accountNonLocked=true;
	    this.credentialsNonExpired=true;
	    this.authorities=authorities;
	}

	public CustomUserDetails(long id, String displayName, String password, String username, Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked, boolean credentialsNonExpired, Collection<? extends GrantedAuthority> authorities) {
	        this.authorities = authorities;
	        this.id = id;
	        this.password = password;
	        this.username = username;
	        this.enabled = enabled;
	        this.accountNonExpired = accountNonExpired;
	        this.accountNonLocked = accountNonLocked;
	        this.credentialsNonExpired = credentialsNonExpired;
	    }
	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		@Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }

	    @Override
	    public String getPassword() {
	        return password;
	    }

	    @Override
	    public String getUsername() {
	        return username;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return accountNonExpired;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return accountNonLocked;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return credentialsNonExpired;
	    }

	    @Override
	    public boolean isEnabled() {
	        return enabled;
	    }

	    public void eraseCredentials(){
	        this.password=null;
	    }
}

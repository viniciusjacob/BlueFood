package com.bluefood.infrastructure.web.security;

import com.bluefood.domain.cliente.ClienteRepository;
import com.bluefood.domain.restaurante.RestauranteRepository;
import com.bluefood.domain.usario.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = clienteRepository.findByEmail(username);
		
		if (usuario == null) {
			
			usuario = restauranteRepository.findByEmail(username);
			
			if (usuario == null) {
				
				throw new UsernameNotFoundException(username);
			}
		}
		
		return new LoggedUser(usuario);
	}

}

package com.esgi.api_project_annuel;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.RoleRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.command.UserCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApiProjectAnnuelApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApiProjectAnnuelApplication.class, args);
	}


	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, UserCommand userCommand){
		return args -> {
			roleRepository.save(createRole("USER"));
			roleRepository.save(createRole("ADMIN"));

			userRepository.save(createUser("David","Arnaud","david@hotmail.fr","coucou"));
			userRepository.save(createUser("Lucas","Jehanno","lucas@hotmail.fr","coucou"));

			userCommand.assignUserWithRole(2,1);
			userCommand.assignUserWithRole(2,2);

		};
	}

	private Role createRole(String nameRole){
		Role role = new Role();
		role.setTitlePermission(nameRole);
		return role;
	}

	private User createUser(String firstName, String lastName, String email, String password){
		User user = new User();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);

		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setPseudo(firstName+lastName);
		user.setPassword(encodedPassword);
		user.setEmail(email);
		return user;
	}

}

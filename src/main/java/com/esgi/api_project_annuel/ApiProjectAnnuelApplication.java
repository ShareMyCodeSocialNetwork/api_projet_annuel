package com.esgi.api_project_annuel;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.RoleRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository){
		return args -> {
			var role_USER = roleRepository.save(createRole("USER"));
			var role_ADMIN = roleRepository.save(createRole("ADMIN"));

			System.out.println("----------------------------");
			System.out.println(role_USER.getId());
			System.out.println(role_USER.getTitlePermission());
			System.out.println(role_ADMIN.getId());
			System.out.println(role_ADMIN.getTitlePermission());
			System.out.println("----------------------------");

			var saved_user1 = userRepository.save(createUser("David","Arnaud","david@hotmail.fr","coucou"));
			var saved_user2 = userRepository.save(createUser("Lucas","Jehanno","lucas@hotmail.fr","azerty"));

			saved_user1.setRoles(role_USER);
			saved_user2.setRoles(role_ADMIN);
			userRepository.save(saved_user1);
			userRepository.save(saved_user2);

			System.out.println("----------------------------");
			System.out.println(saved_user1.getId());
			System.out.println(saved_user1.getPseudo());
			System.out.println(saved_user1.getEmail());
			System.out.println(saved_user1.getRoles().getTitlePermission());
			System.out.println(saved_user2.getId());
			System.out.println(saved_user2.getPseudo());
			System.out.println(saved_user2.getEmail());
			System.out.println(saved_user2.getRoles().getTitlePermission());
			System.out.println("----------------------------");
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

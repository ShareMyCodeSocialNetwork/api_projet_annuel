package com.esgi.api_project_annuel;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.FollowRepository;
import com.esgi.api_project_annuel.Domain.repository.LanguageRepository;
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
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, LanguageRepository languageRepository, FollowRepository followRepository){
		return args -> {
			var role_USER = roleRepository.findRoleByTitlePermission("USER");
			if(role_USER == null)
				role_USER = roleRepository.save(createRole("USER"));

			var role_ADMIN = roleRepository.findRoleByTitlePermission("ADMIN");
			if (role_ADMIN == null)
				role_ADMIN = roleRepository.save(createRole("ADMIN"));


			var saved_user1 = userRepository.findByEmail("lucas@hotmail.fr");
			if (saved_user1 == null)
				saved_user1 = userRepository.save(createUser("Lucas","Jehanno","lucas@hotmail.fr","azerty1234"));

			var saved_user2 = userRepository.findByEmail("test@test.fr");
			if (saved_user2 == null)
				saved_user2 = userRepository.save(createUser("Test","Test","test@test.fr","test1234test"));

			var saved_user3 = userRepository.findByEmail("david@hotmail.fr");
			if (saved_user3 == null)
				saved_user3 = userRepository.save(createUser("David","Arnaud","david@hotmail.fr","coucou1234"));


			if (saved_user1.getRoles() == null){
				saved_user1.setRoles(role_ADMIN);
				userRepository.save(saved_user1);
			}
			if (saved_user2.getRoles() == null){
				saved_user2.setRoles(role_USER);
				userRepository.save(saved_user2);
			}
			if (saved_user3.getRoles() == null){
				saved_user3.setRoles(role_ADMIN);
				userRepository.save(saved_user3);
			}

			var js = languageRepository.findByName("js");
			if (js == null)
				js = languageRepository.save(createLanguage("js"));

			var python = languageRepository.findByName("python");
			if (python == null)
				python = languageRepository.save(createLanguage("python"));
			//languageRepository.save(createLanguage("java"));
			var ruby = languageRepository.findByName("ruby");
			if (ruby == null)
				ruby = languageRepository.save(createLanguage("ruby"));



			var f1 = followRepository.getFollowByFollowedUserAndFollowerUser(saved_user1, saved_user3);
			if (f1 == null){
				f1 = followRepository.save(new Follow());
				f1.setFollowedUser(saved_user1);
				f1.setFollowerUser(saved_user3);
				followRepository.save(f1);
			}

			var f2 = followRepository.getFollowByFollowedUserAndFollowerUser(saved_user2, saved_user3);
			if (f2 == null){
				f2 = followRepository.save(new Follow());
				f2.setFollowedUser(saved_user2);
				f2.setFollowerUser(saved_user3);
				followRepository.save(f2);
			}

			var f3 = followRepository.getFollowByFollowedUserAndFollowerUser(saved_user3, saved_user1);
			if (f3 == null){
				f3 = followRepository.save(new Follow());
				f3.setFollowedUser(saved_user3);
				f3.setFollowerUser(saved_user1);
				followRepository.save(f3);
			}

			var f4 = followRepository.getFollowByFollowedUserAndFollowerUser(saved_user2, saved_user1);
			if (f4 == null){
				f4 = followRepository.save(new Follow());
				f4.setFollowedUser(saved_user2);
				f4.setFollowerUser(saved_user1);
				followRepository.save(f4);
			}

			System.out.println("Const datas : ");
			System.out.println("----------------------------");
			System.out.println("------------roles-----------");
			System.out.println(role_USER.getId());
			System.out.println(role_USER.getTitlePermission());
			System.out.println(role_ADMIN.getId());
			System.out.println(role_ADMIN.getTitlePermission());
			System.out.println("------------roles-----------");
			System.out.println("----------------------------");
			System.out.println("----------languages---------");
			System.out.println("js : " + js.getName() + ", id : " + js.getId());
			System.out.println("python : " + python.getName() + ", id : " + python.getId());
			System.out.println("ruby : " + ruby.getName() + ", id : " + ruby.getId());
			System.out.println("----------languages---------");
			System.out.println("----------------------------");
			System.out.println("------------users-----------");
			System.out.println(saved_user1.getId());
			System.out.println(saved_user1.getPseudo());
			System.out.println(saved_user1.getEmail());
			System.out.println(saved_user1.getRoles().getTitlePermission());
			System.out.println(saved_user2.getId());
			System.out.println(saved_user2.getPseudo());
			System.out.println(saved_user2.getEmail());
			System.out.println(saved_user2.getRoles().getTitlePermission());
			System.out.println(saved_user3.getId());
			System.out.println(saved_user3.getPseudo());
			System.out.println(saved_user3.getEmail());
			System.out.println(saved_user3.getRoles().getTitlePermission());
			System.out.println("------------users-----------");
			System.out.println("----------------------------");

		};
	}
	private Follow createFollow(User follower, User followed){
		var follow = new Follow();
		follow.setFollowerUser(follower);
		follow.setFollowedUser(followed);
		return follow;
	}
	private Role createRole(String nameRole){
		Role role = new Role();
		role.setTitlePermission(nameRole);
		return role;
	}
	private Language createLanguage(String languageName){
		Language language = new Language();
		language.setName(languageName);
		return language;
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

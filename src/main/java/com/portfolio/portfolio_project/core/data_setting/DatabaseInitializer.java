// package com.portfolio.portfolio_project.core.data_setting;

// import java.time.LocalDateTime;
// import java.util.Optional;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.portfolio.portfolio_project.domain.jpa.myproject.enums.ProjectRole;
// import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCode;
// import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCodeRepository;
// import com.portfolio.portfolio_project.domain.jpa.skills.enums.SkillType;
// import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;
// import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCodeRepository;
// import com.portfolio.portfolio_project.domain.jpa.user.User;
// import com.portfolio.portfolio_project.domain.jpa.user.UserRepository;

// import lombok.RequiredArgsConstructor;

// @Component
// @RequiredArgsConstructor
// public class DatabaseInitializer implements CommandLineRunner {

//     private final UserRepository userRepository;
//     private final MyProjectRoleCodeRepository myProjectRoleCodeRepository;
//     private final MySkillTypeCodeRepository mySkillTypeCodeRepository;
//     private final BCryptPasswordEncoder bCryptPasswordEncoder;
//     @Override
//     public void run(String... args) {

//         Optional<User> userCheck = userRepository.findByEmail("aozp73@naver.com");

//         if (!userCheck.isPresent()) {
//             String encPassword = bCryptPasswordEncoder.encode("1234");
//             User user = User.builder()
//                     .email("aozp73@naver.com")
//                     .password(encPassword)
//                     .role("admin")
//                     .createdAt(LocalDateTime.now())
//                     .build();
//             userRepository.save(user);
//         }

//         for (ProjectRole role : ProjectRole.values()) {
//             MyProjectRoleCode myProjectRoleCode = MyProjectRoleCode.builder()
//                 .projectRole(role)
//                 .build();
//             Optional<MyProjectRoleCode> myProjectRoleCodeCheck = myProjectRoleCodeRepository.findByProjectRole(role);
//             if (!myProjectRoleCodeCheck.isPresent()) {
//                 myProjectRoleCodeRepository.save(myProjectRoleCode);
//             }
//         }
//         for (SkillType type : SkillType.values()) {
//             MySkillTypeCode mySkillTypeCode = MySkillTypeCode.builder()
//                 .skillType(type)
//                 .build();
//             Optional<MySkillTypeCode> mySkillTypeCodeCheck = mySkillTypeCodeRepository.findBySkillType(type);
//             if (!mySkillTypeCodeCheck.isPresent()) {
//                 mySkillTypeCodeRepository.save(mySkillTypeCode);
//             }
//         }
//     }   
// }
package study.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/members")
public class ValidationController {
    @GetMapping("/manual_bean_validation")
    public String memberBeanValidation() {
        // 어떠한 종류의 필드값도 없는 상황이므로 여러 검증 에러가 발생
        Member m = new Member();
    /*
    m.setName("김철수");
    m.setAge(20);
    m.setGender("남성");
    m.setEmail("hello@hello.com");
    */

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // 실제 검증 작업을 수행할 Validator 객체 얻어오기
        Validator validator = factory.getValidator();
        // validate 메서드로 객체의 검증 작업 진행, 결과로 검증 에러가 포함된 집합(Set) 객체를 얻을 수 있음
        Set<ConstraintViolation<Member>> violationSet = validator.validate(m);

        String result = "";
        // 모든 검증 에러 확인하기
        for(ConstraintViolation<Member> v : violationSet) {
            result += String.format("field : %s, value : '%s', message : %s",
                    v.getPropertyPath(),
                    v.getInvalidValue(),
                    v.getMessage()) + "\n";
        }
        System.out.println(result);

        return result;
    }
}

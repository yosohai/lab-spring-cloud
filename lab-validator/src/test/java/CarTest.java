import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lab.validator.bean.Car;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

public class CarTest {

    private static Validator validator;

    public static Validator getValidatorByLocale(Locale locale) {
        Locale.setDefault(locale);
        Validator validator = Validation.byDefaultProvider().configure()
                .messageInterpolator(
                        new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("MyMessages")))
                .buildValidatorFactory().getValidator();
        return validator;
    }

    @BeforeClass
    public static void setUp() {
        Locale locale = new Locale("en", "US");
        locale = Locale.getDefault();
        System.out.println(locale.getLanguage());
        System.out.println(locale.getCountry());
        Locale.setDefault(locale);
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .messageInterpolator(
                        new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("MyMessages")))
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void manufacturerIsNull() {
        Car car = new Car("", "DD-AB-123", 1);

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        constraintViolations.forEach(r -> System.out.println(r.getPropertyPath() + ":" + r.getMessage()));
    }

    @Test
    public void licensePlateTooShort() {
        Car car = new Car("Morris", "8", 4);

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);
        constraintViolations.forEach(r -> System.out.println(r.getPropertyPath() + ":" + r.getMessage()));
    }

    @Test
    public void seatCountTooLow() {
        Car car = new Car("Morris", "DD-AB-123", 1);

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);
        constraintViolations.forEach(r -> System.out.println(r.getPropertyPath() + ":" + r.getMessage()));
    }

    @Test
    public void carIsValid() {
        Car car = new Car("Morris", "DD-AB-123", 2);

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);
        constraintViolations.forEach(r -> System.out.println(r.getPropertyPath() + ":" + r.getMessage()));
    }
}
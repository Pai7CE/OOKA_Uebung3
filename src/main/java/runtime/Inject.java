package runtime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

@Target(value={METHOD,CONSTRUCTOR,FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
}

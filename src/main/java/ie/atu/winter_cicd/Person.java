package ie.atu.winter_cicd;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.DefaultMessageCodesResolver;

import java.text.Format;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person
{
    @NotBlank(message ="Code can't be blank, Must be in format STU-XXXX")
    @Min(value = 8, message = "Code must fit standard")
    @Max(value = 8, message = "Code must fit standard")
    private String pCode;

    @NotBlank(message ="Name can't be blank")
    @Max(value = 100, message = "Must have less than 100 characters")
    private String pName;

    //Year of birth
    @NotBlank(message ="Year can't be blank, must be Electronics, Apparel or Furniture")
    private int year;

    @NotBlank(message = "ClassNum/FormNum can't be blank")
    private String classNum;

    @NotBlank(message = "Gender can't be blank")
    private String gender;

    //A timestamp for time of info update
    @NotBlank(message ="Timestamp can't be blank")
    private String time;
}

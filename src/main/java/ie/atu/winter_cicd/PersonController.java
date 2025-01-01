package ie.atu.winter_cicd;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController
{
    public List<Person> product = new ArrayList<>();

    @GetMapping("/list")
    public List<Person> getProduct()
    {
        return product;
    }

    @PostMapping( "/product")
    public List postProduct(@RequestBody @Valid Person newPerson)
    {
        product.add(newPerson);
        return product;
    }

    @PutMapping("/update/{pCode}")
    public List update(@PathVariable String pCode, @RequestBody @Valid Person newPerson)
    {
        product.remove(pCode);
        product.add(newPerson);
        return product;
    }

    @DeleteMapping("/delete/{pCode}")
    public List delete(@PathVariable String pCode)
    {
        product.remove(pCode);
        return product;
    }
}

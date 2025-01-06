package ie.atu.winter_cicd;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController
{
    public List<Person> person = new ArrayList<>();

    @GetMapping("/list")
    public List<Person> getList()
    {
        return person;
    }

    @PostMapping( "/product")
    public List postPerson(@RequestBody @Valid Person newPerson)
    {
        person.add(newPerson);
        return person;
    }

    @PutMapping("/update/{pCode}")
    public List updatePerson(@PathVariable String pCode, @RequestBody @Valid Person newPerson)
    {
        person.remove(pCode);
        person.add(newPerson);
        return person;
    }

    @DeleteMapping("/delete/{pCode}")
    public List delete(@PathVariable String pCode)
    {
        person.remove(pCode);
        return person;
    }
}

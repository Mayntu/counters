package test.group.counters.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
import test.group.counters.CustomExceptions.CounterNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterException;
import test.group.counters.dto.CreateCounterRequest;
import test.group.counters.dto.InsertCounterDTO;
import test.group.counters.models.CounterModel;
import test.group.counters.models.Role;
import test.group.counters.models.UserModel;
import test.group.counters.repositories.CounterRepository;
import test.group.counters.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class CounterService
{
    private final CounterRepository counterRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CounterService(CounterRepository counterRepository, UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.counterRepository = counterRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public CounterModel get(Long id) throws CounterGroupNotFoundException {
        return counterRepository.findById(id).orElseThrow(CounterNotFoundException::new);
    }

    public InsertCounterDTO insert(CreateCounterRequest createCounterRequest)
    {
        String username = "COUNTER_" + createCounterRequest.getName();
        String password = generatePassword();

        try
        {
            UserModel userModel = new UserModel(username, passwordEncoder.encode(password), Role.METER);
            userRepository.save(userModel);

            CounterModel counterModel = new CounterModel(createCounterRequest.getName(), createCounterRequest.getGroupName(), userModel);
            counterRepository.save(counterModel);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidCounterException();
        } catch (Exception e) {
            throw new ServerErrorException("server not working", e);
        }

        InsertCounterDTO insertCounterDTO = new InsertCounterDTO(username, password);

        return insertCounterDTO;
    }

    private String generatePassword()
    {
        return "12345";
    }
}

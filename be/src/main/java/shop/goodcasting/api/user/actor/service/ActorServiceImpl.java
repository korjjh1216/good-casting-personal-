package shop.goodcasting.api.user.actor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.goodcasting.api.user.actor.domain.Actor;
import shop.goodcasting.api.user.actor.domain.ActorDTO;
import shop.goodcasting.api.user.actor.repository.ActorRepository;
import shop.goodcasting.api.user.login.domain.UserVO;
import shop.goodcasting.api.user.login.repository.UserRepository;
import shop.goodcasting.api.user.login.service.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final UserRepository userRepository;
    private final ActorRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Actor> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Actor> findById(Long actorId) {
        return repo.findById(actorId);
    }

    @Transactional
    @Override
    public Long delete(ActorDTO actorDTO) {
        Actor actor = dto2EntityAll(actorDTO);

//        repo.update(actor.getUserVO().getUserId(), false);
//        repo.delete(actor);

        return repo.findById(actor.getActorId()).orElse(null) == null ? 1L : 0L;
    }

    @Override
    @Transactional
    public ActorDTO moreDetail(ActorDTO actorDTO) {
        String passwordUp =  passwordEncoder.encode(actorDTO.getUser().getPassword());
        repo.passwordUpdate(actorDTO.getUser().getUserId(),passwordUp);
        Actor actor = dto2EntityAll(actorDTO);
        repo.save(actor);
        return null;
    }
}
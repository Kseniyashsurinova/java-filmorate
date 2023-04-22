package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.LikeService;

@RestController
@RequestMapping("/films/{id}/like/{userId}")
@Slf4j
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PutMapping
    public void addLikes(@PathVariable int id, @PathVariable int userId) {
        log.debug("пользователь ставит лайк фильму");
        likeService.addLike(id, userId);
    }

    @DeleteMapping
    public void removeLikes(@PathVariable int id, @PathVariable int userId) {
        log.debug("пользователь удаляет лайк");
        likeService.removeLikes(id, userId);
    }

}

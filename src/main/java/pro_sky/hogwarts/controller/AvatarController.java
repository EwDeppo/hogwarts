package pro_sky.hogwarts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro_sky.hogwarts.entity.Avatar;
import pro_sky.hogwarts.service.AvatarService;

import java.util.Collection;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @GetMapping
    public ResponseEntity<Collection<Avatar>> getAll(@RequestParam("page") Integer page,
                                                     @RequestParam("size") Integer size) {
        Collection<Avatar> avatars = avatarService.findAll(page, size);
        return ResponseEntity.ok(avatars);
    }
}

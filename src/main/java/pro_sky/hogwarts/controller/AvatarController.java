package pro_sky.hogwarts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro_sky.hogwarts.entity.Avatar;
import pro_sky.hogwarts.service.AvatarService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping
    public ResponseEntity<Collection<Avatar>> getAll(@RequestParam("page") Integer page,
                                                @RequestParam("size") Integer size) {
        Collection<Avatar> avatars = avatarService.findAll(page, size);
        return ResponseEntity.ok(avatars);
    }
}

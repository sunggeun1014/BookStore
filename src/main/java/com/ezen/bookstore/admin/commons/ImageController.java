package com.ezen.bookstore.admin.commons;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    private final String IMAGE_DIR = "src/main/resources/static/common/img/profile/";
    private final String DEFAULT_IMAGE = "default_profile.png"; // 기본 이미지 파일 이름

    @GetMapping("/admin/common/img/profile/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            // 불법적인 경로 접근 방지를 위한 파일 이름 검증
            if (filename.contains("..")) {
                return ResponseEntity.badRequest().build();
            }

            Path filePath = Paths.get(IMAGE_DIR).resolve(filename).normalize();
            if (!Files.exists(filePath)) {
                // 파일이 존재하지 않을 경우 기본 프로필 이미지를 반환
                filePath = Paths.get(IMAGE_DIR).resolve(DEFAULT_IMAGE).normalize();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

package org.whatismytree.wimt.image.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.whatismytree.wimt.image.controller.dto.ImageUploadResponse
import org.whatismytree.wimt.image.service.ImageService

@Tag(name = "이미지 API", description = "이미지 API")
@RestController
@RequestMapping("/v1/images")
class ImageController(
    private val imageService: ImageService,
) {
    @Operation(summary = "이미지 업로드를 한다")
    @PostMapping
    fun upload(@RequestPart file: MultipartFile): ImageUploadResponse {
        val response = imageService.upload(IMAGE_PATH, file)
        return ImageUploadResponse.of(response)
    }

    companion object {
        private const val IMAGE_PATH = "images"
    }
}

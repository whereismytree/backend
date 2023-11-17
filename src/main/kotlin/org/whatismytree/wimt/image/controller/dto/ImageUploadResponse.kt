package org.whatismytree.wimt.image.controller.dto

data class ImageUploadResponse(val imageUrl: String) {
    companion object {
        fun of(imageUrl: String): ImageUploadResponse {
            return ImageUploadResponse(imageUrl)
        }
    }
}

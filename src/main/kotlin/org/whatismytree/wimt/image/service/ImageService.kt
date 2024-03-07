package org.whatismytree.wimt.image.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ImageService(private val amazonS3Client: AmazonS3) {

    @Value("\${aws.s3.bucket-name}")
    private lateinit var bucketName: String

    fun upload(
        path: String,
        file: MultipartFile,
    ): String {
        val objectMetadata = ObjectMetadata().apply {
            contentType = file.contentType
        }
        val generatedFileName = generateFileName(file)

        amazonS3Client.putObject(
            PutObjectRequest("$bucketName/$path", generatedFileName, file.inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead),
        )

        return getFileUrl(generatedFileName, path)
    }

    fun delete(imageUrl: String?) {
        val fileName = imageUrl?.substringAfterLast("/") ?: return
        amazonS3Client.deleteObject(DeleteObjectRequest(bucketName, "images/$fileName"))
    }

    private fun getFileUrl(generatedFileName: String, path: String): String {
        val prefix = amazonS3Client.getUrl(bucketName, generatedFileName).toString()
        val split = prefix.split(generatedFileName)
        val url = "${split[0]}$path/$generatedFileName"
        return url
    }

    private fun generateFileName(file: MultipartFile): String {
        val localDateTime = LocalDateTime.now().format(formatter)
        val fileName = file.originalFilename
        val extension = fileName?.substring(fileName.lastIndexOf(".") + 1)
        return "$localDateTime.$extension"
    }

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    }
}

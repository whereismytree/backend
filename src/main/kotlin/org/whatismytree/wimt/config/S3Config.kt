package org.whatismytree.wimt.config
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {

    @Value("\${aws.account.access-key}")
    private lateinit var bucketAccessKey: String

    @Value("\${aws.account.secret-key}")
    private lateinit var bucketSecretKey: String

    @Value("\${aws.region.static}")
    private lateinit var region: String

    @Bean
    fun amazonS3Client(): AmazonS3 {
        return AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(
                        bucketAccessKey,
                        bucketSecretKey,
                    ),
                ),
            )
            .build()
    }
}

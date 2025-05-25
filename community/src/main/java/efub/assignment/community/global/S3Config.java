package efub.assignment.community.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

	@Value("${aws.s3.access-key}")
	private String accessKey;

	@Value("${aws.s3.secret-key}")
	private String secretKey;

	@Value("${aws.s3.region}")
	private String region;

	// s3서비스를 이용하기 위한 S3Client 객체 생성
	@Bean
	public S3Client s3Client() {
		return S3Client.builder()
			.credentialsProvider(this::awsCredentials)
			.region(Region.of(region))
			.build();
	}

	private AwsCredentials awsCredentials() {
		return new AwsCredentials() {
			@Override
			public String accessKeyId() {
				return accessKey;
			}

			@Override
			public String secretAccessKey() {
				return secretKey;
			}
		};
	}
}

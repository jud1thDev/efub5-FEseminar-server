package efub.assignment.community.post.service;

import static efub.assignment.community.global.SecurityUtil.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import efub.assignment.community.member.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

	@Value("${aws.s3.bucket}")
	private String bucket;

	private final S3Client s3Client;

	public String imageUpload(MultipartFile multipartFile, User user) {

		if (multipartFile.isEmpty()) {
			log.info("image is null");
			return "";
		}

		String fileName = user.getUserId().toString() + "/" + LocalDateTime.now();

		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucket)
				.contentType(multipartFile.getContentType())
				.contentLength(multipartFile.getSize())
				.key(fileName)
				.build();
			RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());
			s3Client.putObject(putObjectRequest, requestBody);
		} catch (IOException e) {
			log.error("cannot upload image", e);
			throw new RuntimeException(e);
		}
		GetUrlRequest getUrlRequest = GetUrlRequest.builder()
			.bucket(bucket)
			.key(fileName)
			.build();

		return s3Client.utilities().getUrl(getUrlRequest).toString();
	}
}

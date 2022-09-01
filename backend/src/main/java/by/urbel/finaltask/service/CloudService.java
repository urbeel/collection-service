package by.urbel.finaltask.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudService {
    private final Cloudinary cloudinary;

    private static final String UPLOADING_ERROR_MESSAGE = "Error while uploading file.";

    public String uploadFileToCloud(MultipartFile multipartFile) {
        try {
            return cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap()).get("url").toString();
        } catch (IOException e) {
            log.error(UPLOADING_ERROR_MESSAGE, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, UPLOADING_ERROR_MESSAGE);
        }
    }
}

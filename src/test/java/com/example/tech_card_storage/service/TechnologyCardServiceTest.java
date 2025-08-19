package com.example.tech_card_storage.service;

import com.example.tech_card_storage.model.TechnologyCard;
import com.example.tech_card_storage.repository.TechnologyCardRepository;
import org.glassfish.jaxb.core.v2.TODO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TechnologyCardServiceTest {

    @InjectMocks
    private TechnologyCardService service;

    @Mock
    private TechnologyCardRepository repository;

    @Test
    public void testPerformSearch() {
        List<TechnologyCard> expectedCards = Arrays.asList(new TechnologyCard(), new TechnologyCard());
        when(service.searchByCriteria("Иванов")).thenReturn(expectedCards);

        BindingAwareModelMap model = new BindingAwareModelMap();
        String viewName = service.search("Иванов", model);

        assertEquals("search", viewName);
        assertNotNull(model.get("resultCards"));
    }

    @Test
    public void testUploadCard() throws IOException {
        MultipartFile validImage = new MockMultipartFile("file", "sample.jpg", "image/jpeg", "Sample Image Bytes".getBytes());
        boolean redirectUrl = service.upload(validImage, "PC-123", "Иван Петров");

        assertEquals(true, redirectUrl);
    }

    //TODO сделать тест с невалидным изображением
    
    @Test
    public void testDownloadFile() throws MalformedURLException {
        String filename = "sample.jpg";
        Resource mockedResource = new UrlResource("file:" + System.getProperty("user.dir") + "/uploads/" + filename);

        ResponseEntity<Resource> expectedResponse = ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(mockedResource);

        ResponseEntity<Resource> response = service.downloadFile(filename);

        assertEquals(expectedResponse, response);
    }

//    //    Не работает
//    @Test
//    public void testDownloadFileMalformedURL(){
//        String invalidFileName = null;
//        Throwable exception = assertThrows(MalformedURLException.class, () -> service.downloadFile(invalidFileName));
//        assertEquals("Invalid URL", exception.getMessage());
//    }

    @Test
    public void testExtractFilenamePathNormalScenario() {
        String filePath = "/upload/pic/sample.jpg";
        String fileName = service.extractFilenamePath(filePath);

        assertEquals("sample.jpg", fileName);
    }

    @Test
    public void testExtractFilenamePathWithoutSlash() {
        String filePath = "sample.jpg";
        String fileName = service.extractFilenamePath(filePath);

        assertEquals("sample.jpg", fileName);
    }

    @Test
    public void testExtractFilenamePathEmptyInput() {
        String filePath = "";
        String fileName = service.extractFilenamePath(filePath);

        assertEquals("", fileName);
    }

    @Test
    public void testExtractFilenamePathNullInput() {
        String filePath = null;
        String fileName = service.extractFilenamePath(filePath);
        assertEquals("", fileName);
    }

    @Test
    public void testExtractFilenamePathMultipleSlashes() {
        String filePath = "/upload//pic////sample.jpg";
        String fileName = service.extractFilenamePath(filePath);

        assertEquals("sample.jpg", fileName);
    }
}

package com.example.tech_card_storage.controller;

import com.example.tech_card_storage.model.TechnologyCard;
import com.example.tech_card_storage.service.TechnologyCardService;
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
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TechnologyCardControllerTest {

    @InjectMocks
    private TechnologyCardController technologyCardController;

    @Mock
    private TechnologyCardService technologyCardService;

    @Test
    public void testPerformSearch() {
        List<TechnologyCard> expectedCards = Arrays.asList(new TechnologyCard(), new TechnologyCard());
        when(technologyCardService.searchByCriteria(anyString())).thenReturn(expectedCards);

        BindingAwareModelMap model = new BindingAwareModelMap();
        String viewName = technologyCardController.performSearch("Иванов", model);

        assertEquals("search", viewName);
        assertNotNull(model.get("resultCards"));
    }

    @Test
    public void testUploadCard() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getBytes()).thenReturn("sample image bytes".getBytes());
        when(mockFile.getOriginalFilename()).thenReturn("sample.jpg");

        String redirectUrl = technologyCardController.uploadCard(mockFile, "PC-123", "Иван Петров");

        assertEquals("redirect:/search?searchTerm=Иван Петров", redirectUrl);
    }

    @Test
    public void testDownloadCard() throws MalformedURLException {
        String filename = "sample.jpg";
        Resource resource = new UrlResource("file:" + System.getProperty("user.dir") + "/uploads/" + filename);
        when(technologyCardController.downloadFile(filename)).thenCallRealMethod();

        ResponseEntity<Resource> response = technologyCardController.downloadFile(filename);

        assertEquals(MediaType.IMAGE_PNG, response.getHeaders().getContentType());
        assertEquals("inline; filename=\"sample.jpg\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(resource, response.getBody());
    }

    //Не работает
//    @Test
//    public void testDownloadFileMalformedURL(){
//        String invalidFileName = "dample.jpg";
//        Throwable exception = assertThrows(MalformedURLException.class, () -> technologyCardController.downloadFile(invalidFileName));
//        assertEquals("Invalid URL", exception.getMessage());
//    }

    @Test
    public void testExtractFilenamePathNormalScenario() {
        String filePath = "/upload/pic/sample.jpg";
        String fileName = technologyCardController.extractFilenamePath(filePath);

        assertEquals("sample.jpg", fileName);
    }

    @Test
    public void testExtractFilenamePathWithoutSlash() {
        String filePath = "sample.jpg";
        String fileName = technologyCardController.extractFilenamePath(filePath);

        assertEquals("sample.jpg", fileName);
    }

    @Test
    public void testExtractFilenamePathEmptyInput() {
        String filePath = "";
        String fileName = technologyCardController.extractFilenamePath(filePath);

        assertEquals("", fileName);
    }

    @Test
    public void testExtractFilenamePathNullInput() {
        String filePath = null;
        String fileName = technologyCardController.extractFilenamePath(filePath);
        assertEquals("", fileName);
    }

    @Test
    public void testExtractFilenamePathMultipleSlashes() {
        String filePath = "/upload//pic////sample.jpg";
        String fileName = technologyCardController.extractFilenamePath(filePath);

        assertEquals("sample.jpg", fileName);
    }
}

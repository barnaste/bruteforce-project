package use_case.upload;

import data_access.InMemoryImageDataAccessObject;
import data_access.InMemoryPlantDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.Plant;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import use_case.UserDataAccessInterface;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UploadInteractorTest {

    private InMemoryImageDataAccessObject imageDAO;
    private InMemoryPlantDataAccessObject plantDAO;
    private InMemoryUserDataAccessObject userDAO;

    private UploadInteractor interactor;

    @Before
    public void setUp() {

        imageDAO = InMemoryImageDataAccessObject.getInstance();
        plantDAO = InMemoryPlantDataAccessObject.getInstance();
        userDAO = new InMemoryUserDataAccessObject();

        // Clear databases for test isolation
        imageDAO.deleteAll();
        plantDAO.deleteAll();
        userDAO.setCurrentUsername("testUser");
    }

    @Test
    public void testSaveUploadSuccess() {
        // Mock the escape map
        Runnable mockEscapeMap = mock(Runnable.class);

        // Create a success presenter
        UploadOutputBoundary presenter = new UploadOutputBoundary() {
            @Override
            public void switchToConfirmView(UploadConfirmOutputData outputData) {
                fail("Unexpected call to switchToConfirmView");
            }

            @Override
            public void switchToSelectView(UploadSelectOutputData outputData) {
                fail("Unexpected call to switchToSelectView");
            }

            @Override
            public void switchToResultView(UploadResultOutputData outputData) {
                fail("Unexpected call to switchToResultView");
            }

            @Override
            public void notifyUploadComplete() {
                // Assert that the plant and image were saved correctly
                assertEquals(1, plantDAO.getNumberOfUserPlants("testUser"));
                Plant savedPlant = plantDAO.getUserPlants("testUser").get(0);

                assertNotNull(savedPlant);
                assertEquals("Test Plant", savedPlant.getSpecies());
                assertEquals("Test Family", savedPlant.getFamily());
                assertEquals("Test Species", savedPlant.getScientificName());
                assertEquals("Test Notes", savedPlant.getComments());
                assertTrue(savedPlant.getIsPublic());

                BufferedImage savedImage = imageDAO.getImageFromID(savedPlant.getImageID());
                assertNotNull(savedImage);
            }
        };

        // Initialize the interactor
        interactor = new UploadInteractor(presenter, imageDAO, plantDAO, userDAO);
        interactor.setEscapeMap(mockEscapeMap);

        // Prepare input data
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        String imageID = imageDAO.addImage(testImage);
        UploadSaveInputData inputData = new UploadSaveInputData(
                testImage,
                "Test Plant",
                "Test Family",
                "Test Species",
                "Test Notes",
                true
        );

        // Act
        interactor.saveUpload(inputData);

        // Verify escape map was executed
        verify(mockEscapeMap, times(1)).run();
    }

    @Test
    public void testSwitchToConfirmView() {
        // Create a confirm presenter
        UploadOutputBoundary presenter = new UploadOutputBoundary() {
            @Override
            public void switchToConfirmView(UploadConfirmOutputData outputData) {
                assertEquals("mockImagePath", outputData.getImage());
            }

            @Override
            public void switchToSelectView(UploadSelectOutputData outputData) {
                fail("Unexpected call to switchToSelectView");
            }

            @Override
            public void switchToResultView(UploadResultOutputData outputData) {
                fail("Unexpected call to switchToResultView");
            }

            @Override
            public void notifyUploadComplete() {
                fail("Unexpected call to notifyUploadComplete");
            }
        };

        // Initialize the interactor
        interactor = new UploadInteractor(presenter, imageDAO, plantDAO, userDAO);

        // Prepare input data
        UploadInputData inputData = new UploadInputData("mockImagePath");

        interactor.switchToConfirmView(inputData);
    }

    @Test
    public void testSwitchToSelectView() {
        // Create a select presenter
        UploadOutputBoundary presenter = new UploadOutputBoundary() {
            @Override
            public void switchToConfirmView(UploadConfirmOutputData outputData) {
                fail("Unexpected call to switchToConfirmView");
            }

            @Override
            public void switchToSelectView(UploadSelectOutputData outputData) {
                assertNotNull(outputData);
            }

            @Override
            public void switchToResultView(UploadResultOutputData outputData) {
                fail("Unexpected call to switchToResultView");
            }

            @Override
            public void notifyUploadComplete() {
                fail("Unexpected call to notifyUploadComplete");
            }
        };

        interactor = new UploadInteractor(presenter, imageDAO, plantDAO, userDAO);

        interactor.switchToSelectView();
    }

    @Test
    public void testEscape() {
        // Mock the escape map
        Runnable mockEscapeMap = mock(Runnable.class);

        // Create a minimal presenter just for this test
        UploadOutputBoundary presenter = new UploadOutputBoundary() {
            @Override
            public void switchToConfirmView(UploadConfirmOutputData outputData) {
                fail("Unexpected call to switchToConfirmView");
            }

            @Override
            public void switchToSelectView(UploadSelectOutputData outputData) {
                fail("Unexpected call to switchToSelectView");
            }

            @Override
            public void switchToResultView(UploadResultOutputData outputData) {
                fail("Unexpected call to switchToResultView");
            }

            @Override
            public void notifyUploadComplete() {
                fail("Unexpected call to notifyUploadComplete");
            }
        };

        interactor = new UploadInteractor(presenter, imageDAO, plantDAO, userDAO);
        interactor.setEscapeMap(mockEscapeMap);

        interactor.escape();

        // Verify escape map was executed
        verify(mockEscapeMap, times(1)).run();
    }

    @Test
    public void testUploadImageDataSuccessResponse() throws Exception {
        // Mock HTTP client and response
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);

        // Mock HttpClientBuilder to return our mocked client
        HttpClientBuilder mockHttpClientBuilder = mock(HttpClientBuilder.class);
        when(mockHttpClientBuilder.build()).thenReturn(mockHttpClient);

        // Use try-with-resources to ensure the static mock is properly closed
        try (MockedStatic<HttpClientBuilder> mockedBuilder = mockStatic(HttpClientBuilder.class)) {
            mockedBuilder.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

            // Simulate a success JSON response
            String successResponse = new JSONObject()
                    .put("results", new JSONObject[]{
                            new JSONObject()
                                    .put("species", new JSONObject()
                                            .put("commonNames", new String[]{"MockPlant"})
                                            .put("scientificNameWithoutAuthor", "MockScientificName")
                                            .put("family", new JSONObject().put("scientificNameWithoutAuthor", "MockFamily"))
                                    )
                                    .put("score", 95.5)
                    })
                    .toString();

            when(mockHttpResponse.getEntity()).thenReturn(new StringEntity(successResponse));
            when(mockHttpClient.execute(any())).thenReturn(mockHttpResponse);

            // Create a presenter to verify the expected success behavior
            UploadOutputBoundary presenter = new UploadOutputBoundary() {
                @Override
                public void switchToConfirmView(UploadConfirmOutputData outputData) {
                    fail("Unexpected call to switchToConfirmView");
                }

                @Override
                public void switchToSelectView(UploadSelectOutputData outputData) {
                    fail("Unexpected call to switchToSelectView");
                }

                @Override
                public void switchToResultView(UploadResultOutputData outputData) {
                    // Verify that the correct data is passed to the presenter
                    assertEquals("MockPlant", outputData.getName());
                    assertEquals("MockScientificName", outputData.getScientificName());
                    assertEquals("MockFamily", outputData.getFamily());
                }

                @Override
                public void notifyUploadComplete() {
                    fail("Unexpected call to notifyUploadComplete");
                }
            };

            // Create a temporary file to simulate an image
            File tempFile = File.createTempFile("mockImage", ".jpg");
            tempFile.deleteOnExit();

            // Initialize the interactor
            UploadInteractor interactor = new UploadInteractor(presenter, null, null, null);

            // Prepare input data
            UploadInputData inputData = new UploadInputData(tempFile.getAbsolutePath());

            interactor.uploadImageData(inputData);

            verify(mockHttpClient, times(1)).execute(any());
        }
    }


    @Test
    public void testUploadImageDataWithErrorResponse() throws Exception {
        // Mock HTTP client and response
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);

        // Mock HttpClientBuilder to return our mocked client
        HttpClientBuilder mockHttpClientBuilder = mock(HttpClientBuilder.class);
        when(mockHttpClientBuilder.build()).thenReturn(mockHttpClient);
        mockStatic(HttpClientBuilder.class).when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

        // Simulate an error JSON response
        String errorResponse = new JSONObject()
                .put("error", true)
                .put("message", "Simulated error message from API")
                .toString();

        when(mockHttpResponse.getEntity()).thenReturn(new StringEntity(errorResponse));
        when(mockHttpClient.execute(any())).thenReturn(mockHttpResponse);

        // Create a presenter to verify the expected error behavior
        UploadOutputBoundary presenter = new UploadOutputBoundary() {
            @Override
            public void switchToConfirmView(UploadConfirmOutputData outputData) {
                fail("Unexpected call to switchToConfirmView");
            }

            @Override
            public void switchToSelectView(UploadSelectOutputData outputData) {
                // Verify that the error message is correctly passed to the presenter
                assertNotNull(outputData.getError());
                assertEquals("Simulated error message from API", outputData.getError());
            }

            @Override
            public void switchToResultView(UploadResultOutputData outputData) {
                fail("Unexpected call to switchToResultView");
            }

            @Override
            public void notifyUploadComplete() {
                fail("Unexpected call to notifyUploadComplete");
            }
        };

        // Create a temporary file to simulate an image
        File tempFile = File.createTempFile("mockImage", ".jpg");
        tempFile.deleteOnExit();

        // Initialize the interactor
        UploadInteractor interactor = new UploadInteractor(presenter, null, null, null);

        // Prepare input data
        UploadInputData inputData = new UploadInputData(tempFile.getAbsolutePath());

        // Act
        interactor.uploadImageData(inputData);

        // Verify HTTP client interaction
        verify(mockHttpClient, times(1)).execute(any());
    }

    @Test
    public void testSystemOutPrintlnForIOException() throws Exception {
        // Mock HTTP client and response
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);

        // Simulate an IOException when executing the HTTP request
        when(mockHttpClient.execute(any())).thenThrow(new IOException("Simulated IOException"));

        // Mock HttpClientBuilder to return our mocked client
        HttpClientBuilder mockHttpClientBuilder = mock(HttpClientBuilder.class);
        when(mockHttpClientBuilder.build()).thenReturn(mockHttpClient);

        // Redirect System.out to a custom stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try (MockedStatic<HttpClientBuilder> mockedBuilder = mockStatic(HttpClientBuilder.class)) {
            mockedBuilder.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

            // Use in-memory DAOs
            ImageDataAccessInterface imageDataBase = InMemoryImageDataAccessObject.getInstance();
            PlantDataAccessInterface plantDataBase = InMemoryPlantDataAccessObject.getInstance();
            UserDataAccessInterface userDataBase = InMemoryUserDataAccessObject.getInstance();

            // Define a presenter to ensure no additional calls during the exception
            UploadOutputBoundary presenter = new UploadOutputBoundary() {
                @Override
                public void switchToConfirmView(UploadConfirmOutputData outputData) {
                    fail("Unexpected call to switchToConfirmView");
                }

                @Override
                public void switchToSelectView(UploadSelectOutputData outputData) {
                    fail("Unexpected call to switchToSelectView");
                }

                @Override
                public void switchToResultView(UploadResultOutputData outputData) {
                    fail("Unexpected call to switchToResultView");
                }

                @Override
                public void notifyUploadComplete() {
                    fail("Unexpected call to notifyUploadComplete");
                }
            };

            // Create a temporary file to simulate an image
            File tempFile = File.createTempFile("mockImage", ".jpg");
            tempFile.deleteOnExit();

            // Initialize the interactor
            UploadInteractor interactor = new UploadInteractor(presenter, imageDataBase, plantDataBase, userDataBase);

            // Prepare input data
            UploadInputData inputData = new UploadInputData(tempFile.getAbsolutePath());

            // Act
            interactor.uploadImageData(inputData);

            // Verify HTTP client interaction
            verify(mockHttpClient, times(1)).execute(any());
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }

        // Assert that the exception message was printed to System.out
        String output = outputStream.toString();
        assertTrue(output.contains("Simulated IOException"));
    }

    @Test
    public void testSystemOutPrintlnForJSONException() throws Exception {
        // Mock HTTP client and response
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);

        // Simulate invalid JSON content
        when(mockHttpResponse.getEntity()).thenReturn(new StringEntity("INVALID_JSON"));
        when(mockHttpClient.execute(any())).thenReturn(mockHttpResponse);

        // Mock HttpClientBuilder to return our mocked client
        HttpClientBuilder mockHttpClientBuilder = mock(HttpClientBuilder.class);
        when(mockHttpClientBuilder.build()).thenReturn(mockHttpClient);

        // Redirect System.out to a custom stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try (MockedStatic<HttpClientBuilder> mockedBuilder = mockStatic(HttpClientBuilder.class)) {
            mockedBuilder.when(HttpClientBuilder::create).thenReturn(mockHttpClientBuilder);

            // Use in-memory DAOs
            ImageDataAccessInterface imageDataBase = InMemoryImageDataAccessObject.getInstance();
            PlantDataAccessInterface plantDataBase = InMemoryPlantDataAccessObject.getInstance();
            UserDataAccessInterface userDataAccess = InMemoryUserDataAccessObject.getInstance();

            // Define a presenter
            UploadOutputBoundary presenter = new UploadOutputBoundary() {
                @Override
                public void switchToConfirmView(UploadConfirmOutputData outputData) {
                    fail("Unexpected call to switchToConfirmView");
                }

                @Override
                public void switchToSelectView(UploadSelectOutputData outputData) {
                    fail("Unexpected call to switchToSelectView");
                }

                @Override
                public void switchToResultView(UploadResultOutputData outputData) {
                    fail("Unexpected call to switchToResultView");
                }

                @Override
                public void notifyUploadComplete() {
                    fail("Unexpected call to notifyUploadComplete");
                }
            };

            // Create a temporary file to simulate an image
            File tempFile = File.createTempFile("mockImage", ".jpg");
            tempFile.deleteOnExit();

            UploadInteractor interactor = new UploadInteractor(presenter, imageDataBase, plantDataBase, userDataAccess);

            // Prepare input data
            UploadInputData inputData = new UploadInputData(tempFile.getAbsolutePath());

            interactor.uploadImageData(inputData);

            // Verify HTTP client interaction
            verify(mockHttpClient, times(1)).execute(any());
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }

        // Assert that the exception message was printed to System.out
        String output = outputStream.toString();
        assertTrue(output.contains("A JSONObject text must begin with '{'"));
    }


}

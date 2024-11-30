package use_case.public_plant_info;

import org.junit.Before;
import org.junit.Test;
import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;

import static org.mockito.Mockito.*;

public class PublicPlantInfoInteractorTest {

    private ImageDataAccessInterface mockImageDatabase;
    private PlantDataAccessInterface mockPlantDatabase;
    private PublicPlantInfoInteractor interactor;

    @Before
    public void setUp() {
        // Initialize interactor with mocks
        interactor = new PublicPlantInfoInteractor();
    }

    @Test
    public void testSetEscapeMapAndEscape() {
        // Create a mock Runnable
        Runnable mockEscapeMap = mock(Runnable.class);

        // Set the escape map in the interactor
        interactor.setEscapeMap(mockEscapeMap);

        // Call the escape method
        interactor.escape();

        // Verify that the mock Runnable's run method was called exactly once
        verify(mockEscapeMap, times(1)).run();
    }
}

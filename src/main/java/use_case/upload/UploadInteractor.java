package use_case.upload;

import java.io.File;
import java.io.IOException;

import use_case.ImageDataAccessInterface;
import use_case.PlantDataAccessInterface;
import entity.Plant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class UploadInteractor implements UploadInputBoundary {
    private final UploadOutputBoundary presenter;
    private final ImageDataAccessInterface imageDataBase;
    private final PlantDataAccessInterface plantDataBase;
    private final String currentUser;

    private Runnable escapeMap;

    // plantnet-specific information
    private static final String PROJECT = "all";
    private static final String API_URL = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=";
    private static final String API_PRIVATE_KEY = "2b1015rSKP2VVP2UzoDaqbYI"; // secret

    public UploadInteractor(UploadOutputBoundary uploadOutputBoundary, ImageDataAccessInterface imageDataBase, PlantDataAccessInterface plantDataBase, String currentUser) {
        this.presenter = uploadOutputBoundary;
        this.imageDataBase = imageDataBase;
        this.plantDataBase = plantDataBase;
        this.currentUser = currentUser;
    }

    @Override
    public void switchToConfirmView(UploadInputData inputData) {
        UploadConfirmOutputData outputData = new UploadConfirmOutputData(inputData.getImage());
        this.presenter.switchToConfirmView(outputData);
    }

    @Override
    public void switchToSelectView() {
        this.presenter.switchToSelectView(new UploadSelectOutputData());
    }

    @Override
    public void loadImageData(UploadInputData uploadInputData) {
        File image = new File(uploadInputData.getImage());
        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("images", new FileBody(image)).addTextBody("organs", "auto")
                .build();
        // list of probable species
        HttpPost request = new HttpPost(API_URL + API_PRIVATE_KEY);
        request.setEntity(entity);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse httpResponse;
        // fetch plant information from uploaded image using plantnet api
        try {
            httpResponse = client.execute(request);
            String jsonString = EntityUtils.toString(httpResponse.getEntity());

            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("error")) {
                UploadSelectOutputData outputData = new UploadSelectOutputData(jsonObject.getString("message"));
                this.presenter.switchToSelectView(outputData);
            } else {
                // find relevant information in jsonObject
                String name = jsonObject
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("species")
                        .getJSONArray("commonNames")
                        .getString(0);
                String scientific = jsonObject
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("species")
                        .getString("scientificNameWithoutAuthor");
                String family = jsonObject
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("species")
                        .getJSONObject("family")
                        .getString("scientificNameWithoutAuthor");
                double score = jsonObject
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getDouble("score");

                UploadResultOutputData outputData = new UploadResultOutputData(
                        uploadInputData.getImage(),
                        name,
                        scientific,
                        family,
                        score
                );
                this.presenter.switchToResultView(outputData);
            }
        } catch (JSONException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUpload(UploadSaveInputData inputData) {
        String imageID = imageDataBase.addImage(inputData.getImage());
        Plant plant = new Plant(
                imageID,
                inputData.getPlantName(),
                inputData.getFamily(),
                inputData.getPlantSpecies(),
                this.currentUser,
                inputData.getUserNotes(),
                inputData.isPublic()
        );
        plantDataBase.addPlant(plant);

        this.escapeMap.run();
    }

    public void setEscapeMap(Runnable escapeMap) {
        this.escapeMap = escapeMap;
    }

    public void escape() {
        this.escapeMap.run();
    }
}


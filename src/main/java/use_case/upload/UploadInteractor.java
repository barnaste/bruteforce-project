package use_case.upload;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

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

    private Runnable escapeMap;

    // for plantnet
    private static final String PROJECT = "all";
    private static final String API_URL = "https://my-api.plantnet.org/v2/identify/" + PROJECT + "?api-key=";
    private static final String API_PRIVATE_KEY = "2b10zA9aJVKs90Ge5DEINUTouO"; // secret

    public UploadInteractor(UploadOutputBoundary uploadOutputBoundary) {
        this.presenter = uploadOutputBoundary;
    }

    @Override
    public void switchToConfirmView(UploadInputData inputData) {
        UploadOutputData outputData = new UploadOutputData(inputData.getImage());
        this.presenter.switchToConfirmView(outputData);
    }

    @Override
    public void switchToResultView(UploadInputData uploadInputData) {
        UploadOutputData outputData = new UploadOutputData(uploadInputData.getImage());
        this.presenter.switchToResultView(outputData);
    }

    @Override
    public void switchToSelectView() {
        this.presenter.switchToSelectView();
    }

    @Override
    public void saveUpload() {}

    public void setEscapeMap(Runnable escapeMap) {
        this.escapeMap = escapeMap;
    }

    public void escape() {
        this.escapeMap.run();
    }

    public void execute(UploadInputData uploadInputData) {
        File filePath = new File("");

        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("images", new FileBody(filePath)).addTextBody("organs", "auto")
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

            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                // TODO: what happens when no plant is recognized -- can happen often

                // find relevant information in jsonObject
                double score = jsonObject
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getDouble("score");
                String scientific = jsonObject
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("species")
                        .getString("scientificNameWithoutAuthor");

                System.out.println(score + " " + scientific);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // TODO:
    //  1. fetch plant name from an image to plant name api
    //  2. fetch plant details from trefle (this requires that we curl from java somehow)

}


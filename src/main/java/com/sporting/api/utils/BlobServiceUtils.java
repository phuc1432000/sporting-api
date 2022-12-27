package com.sporting.api.utils;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@Slf4j
public class BlobServiceUtils {
    @Value("${blob.connect.string}")
    String connectStr;

    @Value("${blob.container}")
    String container;

    /**
     * Upload and Download file
     *
     * @param file
     * @return
     */
    public Boolean uploadAndDownloadFile(MultipartFile file) {
        try {
            // Create a BlobServiceClient object which will be used to create a container
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectStr)
                    .buildClient();
            // Create the container and return
            // BlobServiceClient containerService container client object
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
            // Get a reference to a blob
            BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());
            // Note: We are creating BlockBlob instance.
            BlockBlobClient blockBlobClient = blobClient.getBlockBlobClient();

            blockBlobClient.upload(file.getInputStream(), file.getSize());
            return true;
        } catch (Exception e) {
            log.error("UploadAndDownloadFile", e);
        }
        return false;
    }

    public static String convertBlobToString(Blob blob) {
        byte[] bdata = new byte[0];
        try {
            bdata = blob.getBytes(1, (int) blob.length());
            String text = new String(bdata);
            return text;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param finput
     * @param fileName
     * @return
     */
    public String uploadFile(InputStream finput, String fileName) {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectStr).buildClient();
            // Create the container and return a container client object
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
            // Get a reference to a blob
            String[] data = fileName.split("\\.");
            fileName = data[0] + System.currentTimeMillis() + "." + data[1];
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            log.info("Uploading to Blob storage as blob:\n" + blobClient.getBlobUrl());
            blobClient.upload(BinaryData.fromStream(finput));
            log.info("Uploaded to Blob storage done!!!");
            return fileName;
        } catch (Exception e) {
            log.error("Error uploading file to Blob storage: ", e);
            return null;
        }
    }

    /**
     * @param blobName
     * @return
     */
    public String getUrl(String blobName) {
        // Create a BlobServiceClient object which will be used to create a container client
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectStr).buildClient();
        BlobContainerClient containerClient = blobServiceClient
                .getBlobContainerClient(container);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        return blobClient.getBlobUrl();
    }

    //end
}

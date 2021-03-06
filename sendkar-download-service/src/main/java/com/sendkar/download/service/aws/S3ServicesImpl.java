package com.sendkar.download.service.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class S3ServicesImpl implements S3Services {

    private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
    private static final String SUFFIX = "/";

    @Autowired
    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public ByteArrayOutputStream downloadFile(String keyName) {
        try {
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }

            return baos;
        } catch (IOException ioe) {
            logger.error("IOException: " + ioe.getMessage());
        } catch (AmazonServiceException ase) {
            logger.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
            throw ace;
        }

        return null;
    }

    /**
     * // upload file to folder and set it to public
     * 		String fileName = folderName + SUFFIX + "testvideo.mp4";
     * 		s3client.putObject(new PutObjectRequest(bucketName, fileName,
     * 				new File("C:\\Users\\user\\Desktop\\testvideo.mp4"))
     * 				.withCannedAcl(CannedAccessControlList.PublicRead));
     * @param keyName
     * @param file
     */

    @Override
    public void uploadFile(String keyName, File file) {

        try {
            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            logger.info("===================== Upload File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
    }

    public void createBucket(String bucketName) {
        if(s3client.doesBucketExist(bucketName)) {
            logger.info("Bucket name is not available."
                    + " Try again with a different Bucket name.");
            return;
        }

        s3client.createBucket(bucketName);
    }

    public List<Bucket> getAllBucket() {
        List<Bucket> buckets = s3client.listBuckets();
        return  buckets;
    }

    public void deleteBucket(String bucketName) {
        try {
            s3client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            logger.info(e.getErrorMessage());
            return;
        }
    }

    /** create folder into bucket
     *  String folderName = "testfolder";
     *  createFolder(bucketName, folderName, s3client);
    */
    public void createFolder(String bucketName, String folderName) {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                folderName + SUFFIX, emptyContent, metadata);
        // send request to S3 to create folder
        s3client.putObject(putObjectRequest);
    }

    /**
     * This method first deletes all the files in given folder and than the
     * folder itself
     * deleteFolder(bucketName, folderName, s3client);
     */
    public void deleteFolder(String bucketName, String folderName) {
        List<S3ObjectSummary> fileList =
                s3client.listObjects(bucketName, folderName).getObjectSummaries();
        for (S3ObjectSummary file : fileList) {
            s3client.deleteObject(bucketName, file.getKey());
        }
        s3client.deleteObject(bucketName, folderName);
    }
}

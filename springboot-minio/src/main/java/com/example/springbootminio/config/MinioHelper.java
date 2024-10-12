package com.example.springbootminio.config;

import io.minio.*;
import io.minio.messages.Item;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class MinioHelper {

    private String bucketName = "test";

    private MinioClient minioClient =MinioClient
            .builder()
            .endpoint("http://192.168.56.104:9000")
            .credentials("admin", "12345678")
            .build();


    public void upload(String filename,InputStream is) throws Exception{
        ObjectWriteResponse put = minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filename)
                        // The field file exceeds its maximum permitted size of 1048576 bytes
//                        .stream(is, -1, 1048576)
                        .stream(is, is.available(), -1)
                        .build()
        );
        System.out.println(put.etag());
    }


    public boolean exists(String filename) throws Exception{
        StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(filename).build());
        System.out.println(statObjectResponse);
        return statObjectResponse.size() > 0;
    }

    public void download(String filename, OutputStream os) throws Exception{
        try (InputStream is =
                      minioClient.getObject(
                              GetObjectArgs.builder()
                                      .bucket(bucketName)
                                      .object(filename)
                                      .build());){
            System.out.println(is.available());
            byte[] buf = new byte[16384];
            int bytesRead;
            while ((bytesRead = is.read(buf, 0, buf.length)) >= 0) {
                os.write(buf, 0, bytesRead);
            }
            os.flush();
            os.close();
            // Close the input is.
            is.close();
        }


    }

}

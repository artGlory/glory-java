package com.glory.gloryUtils.example.ceph.rgw.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.glory.gloryUtils.utils.JacksonUtil;
import sun.misc.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CephS3Util {


    public static void main(String[] args) throws IOException {

        String userId = "s3user01";
        String rgwUrlDomain = "10.1.6.79:7480";
        String accessKey = "MLRFYEMKRHSF6H77NEBH";
        String secretKey = "kxa2PHWw03IPPZ7NF5xw9vABViDjNW1xQYpXoETz";
/*
用户1创建的bucket，用户2不是使用
 */
//        userId = "s3user02";
//        accessKey = "AEYU7IBG9PCMEJLWTJH1";
//        secretKey = "zqmjOeAN3eLahSc6UxN4CIjediJDbjrFm49LQ77o";


        AmazonS3 conn = newConnectToService(rgwUrlDomain, accessKey, secretKey);
        String bucketName = "my-new-bucket";
        boolean flag = conn.doesBucketExistV2(bucketName);//是否存在这个bucket
        System.err.println(flag);
        PutObjectResult putObjectResult = putObject(conn, bucketName, UUID.randomUUID().toString(), UUID.randomUUID().toString());
        System.err.println(JacksonUtil.objectToJsonStr(putObjectResult));
        List<String> objNameList = listAllObjectName(conn, bucketName);
        for (String key : objNameList) {
            S3Object s3Object = getObject(conn, bucketName, key);
            String value = new String(IOUtils.readNBytes(s3Object.getObjectContent().getDelegateStream(), Integer.MAX_VALUE));
            System.err.println(key + "==>>" + value);
            setObjectAcl(conn, bucketName, key, CannedAccessControlList.PublicRead);

        }
    }

    /**
     * 新建一个连接
     *
     * @return
     */
    public static AmazonS3 newConnectToService(String rgwDomain, String accessKey, String secretKey) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
        conn.setEndpoint(rgwDomain);
        return conn;
    }


    /**
     * 列出用户的所有 BUCKET
     *
     * @param amazonS3
     * @return
     */
    public static List<Bucket> listBucket(AmazonS3 amazonS3) {
        List<Bucket> buckets = amazonS3.listBuckets();
        return buckets;
    }

    /**
     * @param amazonS3
     * @param bucketName
     * @return
     */
    public static Bucket getBucket(AmazonS3 amazonS3, String bucketName) {
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket bucket : buckets) {
            if (bucket.getName().equals(bucketName))
                return bucket;
        }
        return null;
    }

    /**
     * @param amazonS3
     * @param bucketName
     * @return
     */
    public static boolean isExistBucket(AmazonS3 amazonS3, String bucketName) {
        boolean isExist = amazonS3.doesBucketExistV2(bucketName);
        return isExist;
    }

    /**
     * 新建一个 BUCKET
     *
     * @param amazonS3
     * @param bucketName
     * @return
     */
    public static Bucket createBucket(AmazonS3 amazonS3, String bucketName) {
        boolean isExist = amazonS3.doesBucketExistV2(bucketName);
        if (isExist) {
            List<Bucket> buckets = amazonS3.listBuckets();
            for (Bucket bucket : buckets) {
                if (bucket.getName().equals(bucketName))
                    return bucket;
            }
        }
        return amazonS3.createBucket(bucketName);
    }

    /**
     * 删除 BUCKET
     *
     * @param amazonS3
     * @param bucketName
     * @return
     */
    public static void deleteBucket(AmazonS3 amazonS3, String bucketName) {
        amazonS3.deleteBucket(bucketName);
    }


    public static PutObjectResult putObject(AmazonS3 amazonS3, String bucketName, String key, File file) {
        return amazonS3.putObject(bucketName, key, file);
    }

    public static PutObjectResult putObject(AmazonS3 amazonS3, String bucketName, String key, InputStream input, ObjectMetadata metadata) {
        return amazonS3.putObject(bucketName, key, input, metadata);
    }

    public static PutObjectResult putObject(AmazonS3 amazonS3, String bucketName, String key, String content) {
        return amazonS3.putObject(bucketName, key, content);
    }

    public static S3Object getObject(AmazonS3 amazonS3, String bucketName, String key) {
        return amazonS3.getObject(bucketName, key);
    }

    public static void deleteObject(AmazonS3 amazonS3, String bucketName, String key) {
        amazonS3.deleteObject(bucketName, key);
    }

    /**
     * 设置对象权限
     *
     * @param amazonS3
     * @param bucketName
     * @param key
     * @param cannedAccessControlList
     */
    public static void setObjectAcl(AmazonS3 amazonS3, String bucketName, String key, CannedAccessControlList cannedAccessControlList) {
        amazonS3.setObjectAcl(bucketName, key, cannedAccessControlList);
    }

    /**
     * bucket 内的所有对象名字
     *
     * @param amazonS3
     * @param bucketName
     * @return
     */
    public static List<String> listAllObjectName(AmazonS3 amazonS3, String bucketName) {
        List<String> result = new ArrayList<>();
        ObjectListing objects = amazonS3.listObjects(bucketName);
        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                result.add(objectSummary.getKey());
            }
            objects = amazonS3.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        return result;
    }
}


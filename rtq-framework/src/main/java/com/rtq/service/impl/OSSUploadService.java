package com.rtq.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.rtq.domain.ResponseResult;
import com.rtq.enums.AppHttpCodeEnum;
import com.rtq.exception.SystemException;
import com.rtq.service.UploadService;
import com.rtq.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author rtq
 * @Date 2022/12/30
 **/
@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class OSSUploadService implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult upload(MultipartFile img) {
        //TODO 判断文件类型和大小

        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //
        String filePath = PathUtils.generateFilePath(originalFilename);

        //上传到oss
        String url = uploadOSS(img, filePath);//2022/12/11
        return ResponseResult.okResult(url);
    }

    @Override
    public ResponseResult uploadImg(MultipartFile img) throws IOException {

        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOSS(img, filePath);//七牛云  2099/2/3/wqeqeqe.png
        //String url = uploadFile(filePath,img.getInputStream());//七牛云  2099/2/3/wqeqeqe.png

        return ResponseResult.okResult(url);
    }


    private String uploadOSS(MultipartFile imgFile, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本


        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//        String accessKey = "your access key";
//        String secretKey = "your secret key";
//        String bucket = "tq-blog";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);


            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://oss.rtq.cool/" + filePath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www";
    }
}
//    private String uploadFile(String filePath, InputStream inputStream) {
//        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "weqeq";
//        String accessKeySecret = "wqeqrqrqwe";
//        String bucketName = "tq-blog-oss";
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        // 创建PutObjectRequest对象。
//        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream);
//
//        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//        // ObjectMetadata metadata = new ObjectMetadata();
//        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//        // metadata.setObjectAcl(CannedAccessControlList.Private);
//        // putObjectRequest.setMetadata(metadata);
//
//        // 上传字符串。
//        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();
//        return "https://"+bucketName+"."+endpoint+"/"+filePath;
//    }
//}

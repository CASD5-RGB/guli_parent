package com.atguigu.vodTest;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class Testvod {
    public static void main(String[] args) throws Exception {
//        String accessKeyId = "LTAI4GJ6aa5zmUTuAMmteoss";
//        String accessKeySecret = "53eD9YFsPyTEHxBLxgpyB1mDY4ChHD";
//
//        String title = "6- What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
//        String fileName = "D:/6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
//        //上传视频的方法
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }
        getPlayAuth();
    }
    //1 根据视频iD获取视频播放凭证
    public static void getPlayAuth() throws Exception {

        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tP8drFdf3HHNk6sd6oE", "le7RL6GJB8FGpSjDH74GSy9YeryVlC");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("eaaf74b0834671ed808e0674a2ce0102");

        response = client.getAcsResponse(request);
        System.out.println("playAuth:" + response.getPlayAuth());
    }


    public void getPlayUrl() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI4GJ6aa5zmUTuAMmteoss", "53eD9YFsPyTEHxBLxgpyB1mDY4ChHD");
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id
        request.setVideoId("\n" +
                "eaaf74b0834671ed808e0674a2ce0102");
        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("PlayInfo.PlayURL =" + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.println("VideoBase.Title =" + response.getVideoBase().getTitle() + "\n");


    }
}

package org.example.springbootopenoffice.service;

import lombok.SneakyThrows;
import org.example.springbootopenoffice.FileConvertUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author booty
 */
@Service
public class FileService {


    @SneakyThrows
    public void onlinePreview4urlFile(String url, OutputStream outputStream) throws Exception {
        //获取文件类型
        String[] str = url.split("\\.");

        if(str.length==0){
            throw new Exception("文件格式不正确");
        }
        String suffix = str[str.length-1];
        if(!suffix.equals("txt")
                && !suffix.equals("doc")
                && !suffix.equals("docx")
                && !suffix.equals("xls")
                && !suffix.equals("xlsx")
                && !suffix.equals("ppt")
                && !suffix.equals("pptx")
        ){
            throw new Exception("文件格式不支持预览");
        }
        InputStream in=FileConvertUtil.convertNetFile(url,suffix);
        //创建存放文件内容的数组
        byte[] buff =new byte[1024];
        //所读取的内容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while((n=in.read(buff))!=-1){
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff,0,n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
    }

    @SneakyThrows
    public void onlinePreview4LocalFile(String path, OutputStream outputStream)  {
        //获取文件类型
        String[] str = path.split("\\.");

        if(str.length==0){
            throw new Exception("文件格式不正确");
        }
        String suffix = str[str.length-1];
        if(!suffix.equals("txt")
                && !suffix.equals("doc")
                && !suffix.equals("docx")
                && !suffix.equals("xls")
                && !suffix.equals("xlsx")
                && !suffix.equals("ppt")
                && !suffix.equals("pptx")
        ){
            throw new Exception("文件格式不支持预览");
        }
        InputStream in= FileConvertUtil.convertLocaleFile(path,suffix);
        //创建存放文件内容的数组
        byte[] buff =new byte[1024];
        //所读取的内容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while((n=in.read(buff))!=-1){
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff,0,n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
    }
}

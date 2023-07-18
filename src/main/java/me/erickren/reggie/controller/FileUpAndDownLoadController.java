package me.erickren.reggie.controller;

import me.erickren.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * DateTime: 2023/07/18 - 11:38
 * Author: ErickRen
 */
@RequestMapping("/common")
@RestController
public class FileUpAndDownLoadController {

    @Value("${file.path}")
    private String basePath;

    /**
     * 上传文件
     * @param file 待上传文件
     * @return R Object
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        if (file == null) {
            return R.error("上传文件出错，后台返回为NULL");
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = null;
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            return R.error("上传文件出错，未发现文件后缀名。");
        }
        String fileName = UUID.randomUUID().toString() + suffix;

        file.transferTo(new File(basePath + fileName));
        return R.success(fileName);
    }

    /**
     * 下载文件
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response)  {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}

package com.demo.account.controller;

import com.demo.account.common.Result;
import com.demo.account.entity.HealthInfo;
import com.demo.account.service.HealthService;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Resource
    HealthService healthService;

    @PostMapping("/createHealth")
    @ResponseBody
    Result<?> createHealth(@RequestBody HealthInfo healthInfo){
        return Result.success(healthService.createHealth(healthInfo));
    }

    @PutMapping("/updateHealth")
    @ResponseBody
    Result<?> updateHealth(@RequestBody HealthInfo healthInfo){
        return Result.success(healthService.updateHealth(healthInfo));
    }

    @DeleteMapping("/deleteHealth")
    @ResponseBody
    Result<?> deleteHealth(@RequestParam Integer healthId){
        return Result.success(healthService.deleteHealth(healthId));
    }

    @GetMapping("/getHealth")
    @ResponseBody
    Result<?> getHealth(Integer healthId){
        return Result.success(healthService.getHealth(healthId));
    }

    @GetMapping("/getHealthList")
    @ResponseBody
    Result<?> getHealthList(Integer uid){
        return Result.success(healthService.getHealthList(uid));
    }

    @PostMapping("/upload")
    String upload(MultipartFile file){
        if (file.isEmpty()){
            return "上传失败";
        }

        String originFileName = file.getOriginalFilename();

        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
                "\\src\\main\\resources\\static\\images\\";
        String path = pre + fileName;
        try {
            file.transferTo(new File(path));
            return path;
        } catch (IOException e){
            e.printStackTrace();
        }

        return "upload fail";

    }
}

package cn.dyoon.review.service;

import cn.dyoon.review.controller.param.PolicyPublishParam;
import cn.dyoon.review.controller.vo.PolicyInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PolicyService {
    PolicyInfoVO create(String title, String desc, String uploadUserName, List<MultipartFile> files);

    PolicyInfoVO findById(String policyId);

    void deleteById(String policyId);

    void publish(PolicyPublishParam param);
}

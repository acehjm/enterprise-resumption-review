package cn.dyoon.review.service;

import cn.dyoon.review.controller.param.PolicyListParam;
import cn.dyoon.review.controller.param.PolicyPublishParam;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.controller.vo.PolicyInfoVO;
import cn.dyoon.review.controller.vo.PolicyListVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PolicyService {
    PolicyInfoVO create(String title, String desc, String uploadUserName, List<MultipartFile> files);

    PolicyInfoVO findById(String policyId);

    void deleteById(String policyId);

    PolicyInfoVO publish(PolicyPublishParam param);

    void download(String fileId);

    PageVO<PolicyListVO> getPage(PolicyListParam param);

    void upload(String policyId, String uploadUserName, List<MultipartFile> files);
}

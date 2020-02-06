package cn.dyoon.review.service;

import cn.dyoon.review.controller.param.PolicyParam;
import cn.dyoon.review.controller.param.PolicyPublishParam;
import cn.dyoon.review.controller.vo.PolicyInfoVO;

public interface PolicyService {
    PolicyInfoVO create(PolicyParam param);

    PolicyInfoVO findById(String policyId);

    void deleteById(String policyId);

    void publish(PolicyPublishParam param);
}
